package com.kharkiv.movienight.transport.dto.userevent;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserEventCreateDto {

    @NotNull
    private Long eventId;
}
