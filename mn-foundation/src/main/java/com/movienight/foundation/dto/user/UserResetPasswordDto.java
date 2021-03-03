package com.movienight.foundation.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserResetPasswordDto {

    @NotBlank
    @NotNull
    private String oldPassword;

    @NotBlank
    @NotNull
    @Size(min = 4, message = "Password should not be less than 4 characters")
    private String newPassword;

    @NotBlank
    @NotNull
    @Size(min = 4, message = "Confirm password should not be less than 4 characters")
    private String confirmPassword;
}
