package com.kharkiv.movienight.service.userevent;

import com.kharkiv.movienight.service.utils.actor.ActorService;
import com.kharkiv.movienight.transport.dto.pageable.PageableDto;
import com.kharkiv.movienight.transport.dto.userevent.UserEventCreateDto;
import com.kharkiv.movienight.transport.dto.userevent.UserEventFindDto;
import com.kharkiv.movienight.transport.dto.userevent.UserEventOutcomeDto;

import java.util.List;

public interface UserEventService extends ActorService {

    Long create(UserEventCreateDto dto);

    List<UserEventOutcomeDto> findAll(UserEventFindDto finder, PageableDto pageableDto);

    Long delete(Long id);
}
