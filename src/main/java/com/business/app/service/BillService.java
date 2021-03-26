package com.business.app.service;

import com.business.app.commons.DateOperations;
import com.business.app.commons.FinancialCalc;
import com.business.app.exception.CustomException;
import com.business.app.repository.BillRepository;
import com.business.app.repository.model.Bill;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class BillService {

    public static final String BILL_IS_NULL = "bill is null";
    public static final String USER_ASSOCIATED_WITH_BILL_IS_NULL = "user associated with bill is null";
    public static final String BILL_NOT_FOUND = "Bill not found";
    public static final String PAYMENTS_TO_OTHER_USERS_ARE_NOT_ALLOWED = "Payments to other users are not allowed";
    public static final String THIS_BILL_HAS_ALREADY_BEEN_PAID = "This bill has already been paid";

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private FinancialCalc financialCalc;

    @Autowired
    private DateOperations dateOperations;

    public Bill create(Bill bill) {
        log.info("Creating Bill: {}", bill);
        if (Objects.isNull(bill)) {
            throw new CustomException(BILL_IS_NULL, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (Objects.isNull(bill.getUser()) || Objects.isNull(bill.getUser().getId())) {
            throw new CustomException(USER_ASSOCIATED_WITH_BILL_IS_NULL, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return billRepository.save(bill);
    }

    public List<Bill> listByUserId(Long userId) {
        log.info("Listing Bills for user id: {}", userId);
        return billRepository.findByUserId(userId);
    }

    public List<Bill> listAll() {
        log.info("Listing Bills");
        return billRepository.findAll();
    }

    public Bill billPay(Long billId, Long userId) {
        log.info("Paying Bill. BillId: {}, userId: {}", billId, userId);
        Bill bill = billRepository.findById(billId).orElseThrow(() -> new CustomException(BILL_NOT_FOUND, HttpStatus.NOT_FOUND));
        if(!bill.getUser().getId().equals(userId)){
            throw new CustomException(PAYMENTS_TO_OTHER_USERS_ARE_NOT_ALLOWED, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if(!Objects.isNull(bill.getPaymentDate())){
            throw new CustomException(THIS_BILL_HAS_ALREADY_BEEN_PAID, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        bill.setPaymentDate(LocalDate.now());
        bill.setCorrectedValue(financialCalc.calcCorrectedValue(bill.getPaymentDate(), bill.getDueDate(), bill.getOriginalValue()));
        bill.setNumberOfDaysLate(dateOperations.calculateDayDifference(bill.getPaymentDate(), bill.getDueDate()));
        bill.setFinePercentPerDay(financialCalc.getFineByPaymentDate(bill.getNumberOfDaysLate()).floatValue());
        bill.setInterestPercent(financialCalc.getInterestByPaymentDate(bill.getNumberOfDaysLate()).floatValue());
        return billRepository.save(bill);
    }
}
