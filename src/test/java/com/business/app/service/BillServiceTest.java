package com.business.app.service;

import com.business.app.commons.DateOperations;
import com.business.app.commons.FinancialCalc;
import com.business.app.exception.CustomException;
import com.business.app.repository.BillRepository;
import com.business.app.repository.model.Bill;
import com.business.app.repository.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BillServiceTest {

    @Mock
    private BillRepository billRepository;

    @Mock
    private FinancialCalc financialCalc;

    @Mock
    private DateOperations dateOperations;

    @InjectMocks
    private BillService billService;

    @Test
    void whenListingAllBillsShouldReturnAListOfBills() {
        List<Bill> bills = new ArrayList(Arrays.asList(Bill.builder().id(1L).build(), Bill.builder().id(2L).build()));

        when(billRepository.findAll()).thenReturn(bills);

        List<Bill> returned = billService.listAll();

        assertEquals(bills.size(), returned.size());
    }

    @Test
    void whenFetchingBillByUserIdReturnBillList() {
        Long userId = 1L;
        List<Bill> bills = new ArrayList(Arrays.asList(Bill.builder().id(1L).build(), Bill.builder().id(2L).build()));

        when(billRepository.findByUserId(userId)).thenReturn(bills);

        List<Bill> returned = billService.listByUserId(userId);

        assertEquals(bills.size(), returned.size());
    }

    @Test
    void whenCreatingABillShouldReturnTheBillCreated() {
        Long userId = 10L;
        User user = User.builder().id(userId).build();
        Bill bill = Bill.builder().user(user).build();
        Long createdBillId = 1L;
        Bill createdBill = Bill.builder().id(createdBillId).user(user).build();
        when(billRepository.save(bill)).thenReturn(createdBill);

        Bill returned = billService.create(bill);

        assertEquals(createdBillId, returned.getId());
        assertEquals(userId, returned.getUser().getId());
    }

    @Test
    void whenCreatingABillAndBillIsNullShouldReturnCustomException() {

        Exception exception = assertThrows(CustomException.class, () -> {
            billService.create(null);
        });

        assertEquals(exception.getMessage(), billService.BILL_IS_NULL);
    }

    @Test
    void whenCreatingABillWithoutUserIdShouldReturnCustomException() {

        Bill bill = Bill.builder().build();

        Exception exception = assertThrows(CustomException.class, () -> {
            billService.create(bill);
        });

        assertEquals(exception.getMessage(), billService.USER_ASSOCIATED_WITH_BILL_IS_NULL);
    }

    @Test
    void whenPayingTheBillShouldFillInThePaymentAmounts() {
        Long billId = 10L;
        Long userId = 20L;
        Long daysOfLatePayment = 1L;
        BigDecimal correctedValue = BigDecimal.valueOf(110);
        BigDecimal fineByPaymentDate = BigDecimal.valueOf(0.5);
        BigDecimal interestByPaymentDate = BigDecimal.valueOf(1.5);

        Bill bill = createBill(billId, userId, daysOfLatePayment);

        when(billRepository.findById(billId)).thenReturn(java.util.Optional.ofNullable(bill));
        when(financialCalc.calcCorrectedValue(any(LocalDate.class), eq(bill.getDueDate()), eq(bill.getOriginalValue()))).thenReturn(correctedValue);
        when(dateOperations.calculateDayDifference(any(LocalDate.class), eq(bill.getDueDate()))).thenReturn(daysOfLatePayment);
        when(financialCalc.getFineByPaymentDate(anyLong())).thenReturn(fineByPaymentDate);
        when(financialCalc.getInterestByPaymentDate(anyLong())).thenReturn(interestByPaymentDate);
        when(billRepository.save(bill)).then(returnsFirstArg());

        Bill returned = billService.billPay(billId, userId);

        assertEquals(correctedValue, returned.getCorrectedValue());
        assertEquals(daysOfLatePayment, returned.getNumberOfDaysLate());
        assertEquals(fineByPaymentDate.floatValue(), returned.getFinePercentPerDay());
        assertEquals(interestByPaymentDate.floatValue(), returned.getInterestPercent());
        assertNotNull(returned.getPaymentDate());
    }

    @Test
    void whenPayingTheBillAndUserOfBillNotIsTheSameAsTheUserWhoIsPayingShouldThrowCustomException() {
        Long billId = 10L;
        Long billUserId = 20L;
        Long userId = 30L;
        Long daysOfLatePayment = 1L;

        Bill bill = createBill(billId, userId, daysOfLatePayment);

        when(billRepository.findById(billId)).thenReturn(java.util.Optional.ofNullable(bill));

        Exception exception = assertThrows(CustomException.class, () -> {
            billService.billPay(billId, billUserId);
        });

        assertEquals(exception.getMessage(), billService.PAYMENTS_TO_OTHER_USERS_ARE_NOT_ALLOWED);
    }

    @Test
    void whenPayABillThatHasAlreadyBeenPaidShouldThrowCustomException() {
        Long billId = 10L;
        Long billUserId = 20L;
        Long daysOfLatePayment = 1L;

        Bill bill = createBill(billId, billUserId, daysOfLatePayment);
        bill.setPaymentDate(LocalDate.now());

        when(billRepository.findById(billId)).thenReturn(java.util.Optional.ofNullable(bill));

        Exception exception = assertThrows(CustomException.class, () -> {
            billService.billPay(billId, billUserId);
        });

        assertEquals(exception.getMessage(), billService.THIS_BILL_HAS_ALREADY_BEEN_PAID);
    }

    private Bill createBill(Long billId, Long userId, Long daysOfLatePayment) {
        return Bill.builder().id(billId).name("nome").originalValue(BigDecimal.valueOf(100))
                .dueDate(LocalDate.now().minusDays(daysOfLatePayment)).user(User.builder().id(userId).build()).build();
    }
}
