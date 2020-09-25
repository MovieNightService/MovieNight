package com.kharkiv.movienight.transport.dto;

import com.kharkiv.movienight.persistence.model.user.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class UserOutcomeDto {

    private String firstName;
    private String lastName;
    private String username;
    private Instant dateOfBirth;
    private String phone;
    private String email;
    private byte[] avatar;
    private List<UserRole> roles;
    private Long createdBy;
    private Instant createdAt;
    private Long updatedBy;
    private Instant updatedAt;
    private boolean deleted;
}
