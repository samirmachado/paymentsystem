package com.business.app.controller.dtoconverter;

import com.business.app.commons.DateOperations;
import com.business.app.commons.FinancialCalc;
import com.business.app.controller.dto.BillDto;
import com.business.app.repository.model.Bill;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class BillToBillDto extends AbstractConverter<Bill, BillDto>{

    @Autowired
    private FinancialCalc financialCalc;

    @Autowired
    private DateOperations dateOperations;

    @Override
    protected BillDto convert(Bill bill) {
        if (Objects.isNull(bill)) {
            return null;
        }
        BillDto converted = new ModelMapper().map(bill, BillDto.class);
        if(Objects.isNull(bill.getPaymentDate())){
            bill.setPaymentDate(LocalDate.now());
        }
        converted.setNumberOfDaysLate(dateOperations.calculateDayDifference(bill.getPaymentDate(), bill.getDueDate()));
        converted.setCorrectedValue(financialCalc.calcCorrectedValue(bill.getPaymentDate(), bill.getDueDate(), bill.getOriginalValue()));
        converted.setFinePercentPerDay(financialCalc.getFineByPaymentDate(converted.getNumberOfDaysLate()).floatValue());
        converted.setInterestPercent(financialCalc.getInterestByPaymentDate(converted.getNumberOfDaysLate()).floatValue());
        return converted;
    }

}
