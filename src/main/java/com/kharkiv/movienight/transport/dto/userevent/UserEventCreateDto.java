package com.kharkiv.movienight.transport.dto.userevent;

import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.transport.dto.event.EventOutcomeDto;
import com.kharkiv.movienight.transport.dto.movie.MovieOutcomeDto;
import com.kharkiv.movienight.transport.dto.user.UserOutcomeDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserEventCreateDto {

    @NotNull
    private Long eventId;
}
