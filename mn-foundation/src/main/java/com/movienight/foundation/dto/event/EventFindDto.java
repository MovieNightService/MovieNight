package com.movienight.foundation.dto.event;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class EventFindDto {

    private String name;
    private Instant date;
    private Long userId;
}
