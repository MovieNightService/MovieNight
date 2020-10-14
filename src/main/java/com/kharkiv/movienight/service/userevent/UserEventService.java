package com.kharkiv.movienight.service.userevent;

import com.kharkiv.movienight.service.actor.ActorService;
import com.kharkiv.movienight.transport.dto.userevent.UserEventCreateDto;
import com.kharkiv.movienight.transport.dto.userevent.UserEventOutcomeDto;

import java.util.List;

public interface UserEventService extends ActorService {

    Long create(UserEventCreateDto dto);

    List<UserEventOutcomeDto> findAll();

    Long delete(Long id);
}
