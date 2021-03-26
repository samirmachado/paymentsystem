package com.business.app.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillCreateDto {

    @NotNull(message = "name is a required field")
    private String name;

    @NotNull(message = "originalValue is a required field")
    private BigDecimal originalValue;

    @NotNull(message = "dueDate is a required field")
    private LocalDate dueDate;

    @NotNull(message = "user is a required field")
    private EntityId user;
}
