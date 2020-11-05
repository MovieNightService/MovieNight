package com.kharkiv.movienight.service.event;

import com.kharkiv.movienight.persistence.model.event.Event;
import com.kharkiv.movienight.service.utils.actor.ActorService;
import com.kharkiv.movienight.transport.dto.event.EventCreateDto;
import com.kharkiv.movienight.transport.dto.event.EventFindDto;
import com.kharkiv.movienight.transport.dto.event.EventOutcomeDto;
import com.kharkiv.movienight.transport.dto.event.EventUpdateDto;

import java.util.List;

public interface EventService extends ActorService {

    Long create(EventCreateDto dto);

    Event findByIdAndDeletedFalse(Long id);

    EventOutcomeDto findById(Long id);

    Long delete(Long id);

    Long restore(Long id);

    List<EventOutcomeDto> findAll(EventFindDto finder);

    Long update(Long id, EventUpdateDto dto);
}
