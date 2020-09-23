package com.kharkiv.movienight.persistence.model.user;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    USER,ADMIN,MANAGER;

    @Override
    public String getAuthority() {
        return name();
    }
}
