package com.movienight.foundation.dto.event;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter
@Setter
public class EventUpdateDto {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private Instant date;
}
