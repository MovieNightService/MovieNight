package com.movienight.app.service.userevent;

import com.movienight.app.service.utils.actor.ActorService;
import com.movienight.foundation.dto.pageable.PageableDto;
import com.movienight.foundation.dto.userevent.UserEventCreateDto;
import com.movienight.foundation.dto.userevent.UserEventFindDto;
import com.movienight.foundation.dto.userevent.UserEventOutcomeDto;

import java.util.List;

public interface UserEventService extends ActorService {

    Long create(UserEventCreateDto dto);

    List<UserEventOutcomeDto> findAll(UserEventFindDto finder, PageableDto pageableDto);

    Long delete(Long id);
}
