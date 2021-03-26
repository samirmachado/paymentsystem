package com.business.app.repository.model.constant;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_ADMIN");

    @Getter
    private final String value;

    Role(String value){
        this.value = value;
    }

    public String getAuthority() {
        return name();
    }
}
