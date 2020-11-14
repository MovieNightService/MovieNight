package com.kharkiv.movienight.transport.dto.event;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter
@Setter
public class EventCreateDto {

    @NotBlank
    @NotNull
    private String name;

    @NotNull
    private Instant date;

    @NotNull
    private Long movieId;
}
