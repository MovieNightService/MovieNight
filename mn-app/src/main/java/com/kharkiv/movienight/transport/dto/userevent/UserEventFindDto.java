package com.kharkiv.movienight.transport.dto.userevent;

import com.kharkiv.movienight.transport.dto.event.EventOutcomeDto;
import com.kharkiv.movienight.transport.dto.user.UserOutcomeDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEventFindDto {

    private Long userId;
    private Long eventId;
}
