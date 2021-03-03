package com.movienight.foundation.persistence.model.user;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    USER,ADMIN,MANAGER;

    @Override
    public String getAuthority() {
        return name();
    }
}
