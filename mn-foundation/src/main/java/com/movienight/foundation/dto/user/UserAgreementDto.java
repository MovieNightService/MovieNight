package com.movienight.foundation.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserAgreementDto {

    @NotNull
    private Long userId;

    @NotNull
    private boolean agreement;
}
