package com.movienight.foundation.dto.user;

import com.movienight.foundation.persistence.model.user.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class UserOutcomeDto {

    private Long id;
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
