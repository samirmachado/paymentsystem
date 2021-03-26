package com.business.app.commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FinancialCalcTest {

    @Mock
    private DateOperations dateOperations;

    @Mock
    private EnvironmentVariable env;

    @InjectMocks
    private FinancialCalc financialCalc;

    @BeforeEach
    public void setUp(){
        mockEnv();
    }

    @Test
    void whenCalcCorrectedValueWithOneDayLateShouldReturnCalculatedValue() {

        Long daysLate = 1L;
        LocalDate paymentDate = LocalDate.now();
        LocalDate dueDate = LocalDate.now().minusDays(daysLate);
        BigDecimal originalValue = BigDecimal.valueOf(100);

        when(dateOperations.calculateDayDifference(any(LocalDate.class), any(LocalDate.class))).thenReturn(daysLate);

        BigDecimal returned = financialCalc.calcCorrectedValue(paymentDate, dueDate, originalValue);

        assertEquals(new BigDecimal(102.10).setScale(2, RoundingMode.HALF_UP), returned);
    }

    @Test
    void whenCalcCorrectedValueWithFourLateShouldReturnCalculatedValue() {

        Long daysLate = 4L;
        LocalDate paymentDate = LocalDate.now();
        LocalDate dueDate = LocalDate.now().minusDays(daysLate);
        BigDecimal originalValue = BigDecimal.valueOf(100);

        when(dateOperations.calculateDayDifference(any(LocalDate.class), any(LocalDate.class))).thenReturn(daysLate);

        BigDecimal returned = financialCalc.calcCorrectedValue(paymentDate, dueDate, originalValue);

        assertEquals(new BigDecimal(103.83).setScale(2, RoundingMode.HALF_UP), returned);
    }

    @Test
    void whenCalcCorrectedValueWithSixLateShouldReturnCalculatedValue() {

        Long daysLate = 6L;
        LocalDate paymentDate = LocalDate.now();
        LocalDate dueDate = LocalDate.now().minusDays(daysLate);
        BigDecimal originalValue = BigDecimal.valueOf(100);

        when(dateOperations.calculateDayDifference(any(LocalDate.class), any(LocalDate.class))).thenReturn(daysLate);

        BigDecimal returned = financialCalc.calcCorrectedValue(paymentDate, dueDate, originalValue);

        assertEquals(new BigDecimal(106.90).setScale(2, RoundingMode.HALF_UP), returned);
    }

    @Test
    void whenCalcCorrectedValueWithZeroLateShouldReturnCalculatedValue() {

        Long daysLate = 0L;
        LocalDate paymentDate = LocalDate.now();
        LocalDate dueDate = LocalDate.now().minusDays(daysLate);
        BigDecimal originalValue = BigDecimal.valueOf(100);

        when(dateOperations.calculateDayDifference(any(LocalDate.class), any(LocalDate.class))).thenReturn(daysLate);

        BigDecimal returned = financialCalc.calcCorrectedValue(paymentDate, dueDate, originalValue);

        assertEquals(new BigDecimal(100.00).setScale(2, RoundingMode.HALF_UP), returned);
    }

    @Test
    void whenCalcCorrectedValueFiveDaysBeforeDueDateShouldReturnCalculatedValue() {

        Long daysLate = -5L;
        LocalDate paymentDate = LocalDate.now();
        LocalDate dueDate = LocalDate.now().minusDays(daysLate);
        BigDecimal originalValue = BigDecimal.valueOf(100);

        when(dateOperations.calculateDayDifference(any(LocalDate.class), any(LocalDate.class))).thenReturn(daysLate);

        BigDecimal returned = financialCalc.calcCorrectedValue(paymentDate, dueDate, originalValue);

        assertEquals(new BigDecimal(100.00).setScale(2, RoundingMode.HALF_UP), returned);
    }

    private void mockEnv(){
        env.finePerDayForOneToThreeDays = 0.1f;
        env.finePerDayForFourToFiveDays = 0.2f;
        env.finePerDayForSixDaysOrMore = 0.3f;
        env.interestOneToThreeDays = 2f;
        env.interestFourToFiveDays = 3f;
        env.interestSixDaysOrMore = 5f;
    }
}
