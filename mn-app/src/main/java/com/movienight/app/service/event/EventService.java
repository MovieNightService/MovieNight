package com.movienight.app.service.event;

import com.movienight.app.service.utils.actor.ActorService;
import com.movienight.foundation.dto.event.EventCreateDto;
import com.movienight.foundation.dto.event.EventFindDto;
import com.movienight.foundation.dto.event.EventOutcomeDto;
import com.movienight.foundation.dto.event.EventUpdateDto;
import com.movienight.foundation.dto.pageable.PageableDto;
import com.movienight.foundation.persistence.model.event.Event;

import java.util.List;

public interface EventService extends ActorService {

    Long create(EventCreateDto dto);

    Event findByIdAndDeletedFalse(Long id);

    EventOutcomeDto findById(Long id);

    Long delete(Long id);

    Long restore(Long id);

    List<EventOutcomeDto> findAll(EventFindDto finder, PageableDto pageable);

    Long update(Long id, EventUpdateDto dto);
}
