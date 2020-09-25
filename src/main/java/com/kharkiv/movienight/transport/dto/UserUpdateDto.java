package com.kharkiv.movienight.transport.dto;

import com.kharkiv.movienight.persistence.model.user.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter
@Setter
public class UserUpdateDto {

    @NotBlank
    @NotNull
    private String firstName;

    @NotBlank
    @NotNull
    private String lastName;

    @NotBlank
    @NotNull
    private String username;

    @NotBlank
    @NotNull
    @Email
    private String email;

    @NotNull
    private Instant dateOfBirth;

    @NotBlank
    @NotNull
    private String phone;

    @NotNull
    private byte[] avatar;

}
