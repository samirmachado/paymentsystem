package com.business.app.commons;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentVariable {

    @Value("${rules.payment.interest.1.to.3.days}")
    public Float interestOneToThreeDays;

    @Value("${rules.payment.interest.4.to.5.days}")
    public Float interestFourToFiveDays;

    @Value("${rules.payment.interest.6.days.or.more}")
    public Float interestSixDaysOrMore;

    @Value("${rules.payment.fine.per.day.1.to.3.days}")
    public Float finePerDayForOneToThreeDays;

    @Value("${rules.payment.fine.per.day.4.to.5.days}")
    public Float finePerDayForFourToFiveDays;

    @Value("${rules.payment.fine.per.day.6.days.or.more}")
    public Float finePerDayForSixDaysOrMore;

}
