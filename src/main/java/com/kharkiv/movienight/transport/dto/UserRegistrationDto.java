package com.kharkiv.movienight.transport.dto;

import com.kharkiv.movienight.persistence.model.user.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter
@Setter
public class UserRegistrationDto {

    @NotBlank
    @NotNull
    private String username;

    @NotBlank
    @NotNull
    private String firstName;

    @NotBlank
    @NotNull
    private String lastName;

    @NotNull
    private Instant dateOfBirth;

    @NotBlank
    @NotNull
    private String phone;

    @NotBlank
    @Email
    @NotNull
    private String email;

    private byte[] avatar;

    @NotBlank
    @NotNull
    private String password;

    @NotBlank
    @NotNull
    private String confirmPassword;
}
