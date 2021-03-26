package com.business.app.commons;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Component
@NoArgsConstructor
public class FinancialCalc {

    private EnvironmentVariable env;

    private DateOperations dateOperations;

    @Autowired
    public FinancialCalc(EnvironmentVariable env, DateOperations dateOperations) {
        this.env = env;
        this.dateOperations = dateOperations;
    }

    public BigDecimal calcCorrectedValue(LocalDate paymentDate, LocalDate dueDate, BigDecimal originalValue) {
        Long daysLate = dateOperations.calculateDayDifference(paymentDate, dueDate);
        if(daysLate < 1){
            return originalValue.setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal interest = getInterestByPaymentDate(daysLate);
        BigDecimal fine = getFineByPaymentDate(daysLate);
        BigDecimal paymentValue = originalValue;

        paymentValue = paymentValue.add(percentage(paymentValue, interest));
        for (int i = 0; i < daysLate; i++) {
            paymentValue = paymentValue.add(percentage(paymentValue, fine));
        }
        return paymentValue.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getFineByPaymentDate(Long daysLate) {
        if (daysLate >= 1 && daysLate <= 3) {
            return BigDecimal.valueOf(env.finePerDayForOneToThreeDays);
        } else if (daysLate >= 4 && daysLate <= 5) {
            return BigDecimal.valueOf(env.finePerDayForFourToFiveDays);
        } else if (daysLate >= 6) {
            return BigDecimal.valueOf(env.finePerDayForSixDaysOrMore);
        }
        return new BigDecimal(0);
    }

    public BigDecimal getInterestByPaymentDate(Long daysLate) {
        if (daysLate >= 1 && daysLate <= 3) {
            return BigDecimal.valueOf(env.interestOneToThreeDays);
        } else if (daysLate >= 4 && daysLate <= 5) {
            return BigDecimal.valueOf(env.interestFourToFiveDays);
        } else if (daysLate >= 6) {
            return BigDecimal.valueOf(env.interestSixDaysOrMore);
        }
        return new BigDecimal(0);
    }

    private BigDecimal percentage(BigDecimal base, BigDecimal pct) {
        return base.multiply(pct).divide(new BigDecimal(100));
    }
}
