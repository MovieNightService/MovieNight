package com.kharkiv.movienight.transport.dto;

import com.kharkiv.movienight.persistence.model.user.UserRole;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String confirmPassword;

    @NotNull
    private String email;

    @NotNull
    private boolean agreement;

    @NotNull
    private UserRole role;
}
