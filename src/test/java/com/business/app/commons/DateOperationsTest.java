package com.business.app.commons;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DateOperationsTest {

    @InjectMocks
    private DateOperations dateOperations;

    @Test
    void whenCalculateDayDifferenceWithOneDayReturnOne() {

        Long daysLate = 1L;
        LocalDate date = LocalDate.now();
        LocalDate inRelationTo = LocalDate.now().minusDays(daysLate);

        Long returned = dateOperations.calculateDayDifference(date, inRelationTo);

        assertEquals(daysLate, returned);
    }

    @Test
    void whenCalculateDayDifferenceWithZeroDayReturnOne() {

        Long daysLate = 0L;
        LocalDate date = LocalDate.now();
        LocalDate inRelationTo = LocalDate.now().minusDays(daysLate);

        Long returned = dateOperations.calculateDayDifference(date, inRelationTo);

        assertEquals(daysLate, returned);
    }

    @Test
    void whenCalculateDayDifferenceWithMinusFourDaysReturnOne() {

        Long daysLate = -4L;
        LocalDate date = LocalDate.now();
        LocalDate inRelationTo = LocalDate.now().minusDays(daysLate);

        Long returned = dateOperations.calculateDayDifference(date, inRelationTo);

        assertEquals(daysLate, returned);
    }
}
