package com.movienight.foundation.dto.event;

import com.movienight.foundation.dto.movie.MovieOutcomeDto;
import com.movienight.foundation.dto.user.UserOutcomeDto;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class EventOutcomeDto {

    private Long id;
    private String name;
    private Instant date;
    private MovieOutcomeDto movie;
    private UserOutcomeDto user;
    private Long createdBy;
    private Instant createdAt;
    private Long updatedBy;
    private Instant updatedAt;
    private boolean deleted;
}
