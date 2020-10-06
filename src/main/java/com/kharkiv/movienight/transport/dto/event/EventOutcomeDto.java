package com.kharkiv.movienight.transport.dto.event;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class EventOutcomeDto {

    private String name;
    private Instant date;
    private Long movieId;
    private Long userId;
}
