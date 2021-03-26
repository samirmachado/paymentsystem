package com.business.app.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillDto {

    private Long id;

    private String name;

    private BigDecimal originalValue;

    private BigDecimal correctedValue;

    private Long numberOfDaysLate;

    private Float finePercentPerDay;

    private Float interestPercent;

    private LocalDate dueDate;

    private LocalDate paymentDate;

    private EntityId user;
}
