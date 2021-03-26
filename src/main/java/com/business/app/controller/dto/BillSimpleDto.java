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
public class BillSimpleDto {

    private Long id;

    private String name;

    private BigDecimal originalValue;

    private LocalDate dueDate;

    private EntityId user;
}
