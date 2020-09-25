package com.kharkiv.movienight.transport.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserResetPasswordDto {

    @NotBlank
    @NotNull
    private String oldPassword;

    @NotBlank
    @NotNull
    private String newPassword;

    @NotBlank
    @NotNull
    private String confirmPassword;
}
