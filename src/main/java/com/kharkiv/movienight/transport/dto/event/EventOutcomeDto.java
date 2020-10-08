package com.kharkiv.movienight.transport.dto.event;

import com.kharkiv.movienight.transport.dto.movie.MovieOutcomeDto;
import com.kharkiv.movienight.transport.dto.user.UserOutcomeDto;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class EventOutcomeDto {

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
