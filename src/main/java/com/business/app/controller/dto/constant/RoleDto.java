package com.business.app.controller.dto.constant;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

public enum RoleDto implements GrantedAuthority {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_ADMIN");

    @Getter
    private final String value;

    RoleDto(String value){
        this.value = value;
    }

    public String getAuthority() {
        return name();
    }

}
