package com.movienight.foundation.dto.user;

import com.movienight.foundation.persistence.model.user.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserRoleDto {

    @NotNull
    private Long userId;

    @NotNull
    private UserRole role;
}
