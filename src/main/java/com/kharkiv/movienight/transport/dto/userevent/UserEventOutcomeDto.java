package com.kharkiv.movienight.transport.dto.userevent;

import com.kharkiv.movienight.transport.dto.event.EventOutcomeDto;
import com.kharkiv.movienight.transport.dto.user.UserOutcomeDto;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UserEventOutcomeDto {

    private Long id;
    private UserOutcomeDto user;
    private EventOutcomeDto event;
}
