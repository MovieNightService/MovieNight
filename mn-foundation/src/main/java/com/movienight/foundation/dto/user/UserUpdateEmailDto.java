package com.movienight.foundation.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserUpdateEmailDto {

    @NotBlank
    @NotNull
    @Email
    private String oldEmail;

    @NotBlank
    @NotNull
    @Email
    private String newEmail;

    @NotNull
    @NotBlank
    private String password;
}
