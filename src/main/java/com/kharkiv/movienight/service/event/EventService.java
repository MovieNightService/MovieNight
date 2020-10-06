package com.kharkiv.movienight.service.event;

import com.kharkiv.movienight.persistence.model.event.Event;
import com.kharkiv.movienight.service.actor.ActorService;
import com.kharkiv.movienight.transport.dto.event.EventCreateDto;
import com.kharkiv.movienight.transport.dto.event.EventOutcomeDto;

public interface EventService extends ActorService {

    Long create(EventCreateDto dto);

    Event findByIdAndDeletedFalse(Long id);
}
