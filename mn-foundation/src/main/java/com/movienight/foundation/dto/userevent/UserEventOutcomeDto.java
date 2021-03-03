package com.movienight.foundation.dto.userevent;

import com.movienight.foundation.dto.event.EventOutcomeDto;
import com.movienight.foundation.dto.user.UserOutcomeDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEventOutcomeDto {

    private Long id;
    private UserOutcomeDto user;
    private EventOutcomeDto event;
}
