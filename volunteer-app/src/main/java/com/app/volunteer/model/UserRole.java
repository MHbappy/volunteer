package com.app.volunteer.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    VOLUNTEER, ACCOUNT, LIBRARIAN;
    public String getAuthority() {
        return name();
    }

}
