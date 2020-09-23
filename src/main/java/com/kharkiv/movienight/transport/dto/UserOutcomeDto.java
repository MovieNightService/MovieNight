package com.kharkiv.movienight.transport.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UserOutcomeDto {

    private Long id;
    private String email;
    private boolean agreement;
    private Long createdBy;
    private Instant createdAt;
    private Long updatedBy;
    private Instant updatedAt;
    private boolean deleted;
}
