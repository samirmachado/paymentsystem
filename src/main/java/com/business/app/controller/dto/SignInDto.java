package com.business.app.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInDto {

    @NotNull(message = "username is a required field")
    private String username;
    @NotNull(message = "password is a required field")
    private String password;
}
