package com.kharkiv.movienight.service.userevent;

import com.kharkiv.movienight.service.actor.ActorService;
import com.kharkiv.movienight.transport.dto.userevent.UserEventCreateDto;

public interface UserEventService extends ActorService {

    Long create(UserEventCreateDto dto);
}
