package com.movienight.foundation.dto.user;

import com.movienight.foundation.persistence.model.user.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class UserFindDto {

    private String firstName;
    private String lastName;
    private String username;
    private Instant dateOfBirth;
    private String phone;
    private String email;
    private List<UserRole> roles;
}
