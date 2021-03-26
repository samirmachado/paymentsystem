package com.business.app.commons;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class DateOperations {

    public Long calculateDayDifference(LocalDate date, LocalDate inRelationTo) {
        return ChronoUnit.DAYS.between(date, inRelationTo) * -1;
    }
}
