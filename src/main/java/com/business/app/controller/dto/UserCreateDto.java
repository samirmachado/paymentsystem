package com.business.app.controller.dto;

import com.business.app.controller.dto.constant.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateDto {

    @NotNull(message = "username is a required field")
    private String username;
    @NotNull(message = "email is a required field")
    private String email;
    @NotNull(message = "cpf is a required field")
    private String cpf;
    @NotNull(message = "password is a required field")
    private String password;
    @NotNull(message = "User needs at least one role")
    @Size(min = 1)
    List<RoleDto> roles;
}
