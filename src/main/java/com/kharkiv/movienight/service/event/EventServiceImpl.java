package com.kharkiv.movienight.service.event;

import com.kharkiv.movienight.exception.event.EventNotFoundException;
import com.kharkiv.movienight.persistence.model.event.Event;
import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.repository.EventRepository;
import com.kharkiv.movienight.transport.dto.event.EventCreateDto;
import com.kharkiv.movienight.transport.mapper.event.EventMapper;
import lombok.Setter;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
@Setter(onMethod_ = @Autowired)
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private EventMapper eventMapper;

    @Override
    public Long create(EventCreateDto dto) {
        Event event = eventMapper.toEntity(dto);
        return eventRepository.save(event).getId();
    }

    @Override
    public Event findByIdAndDeletedFalse(Long id) {
        return eventRepository.findByIdAndDeletedFalse(id).orElseThrow(EventNotFoundException::new);
    }
}
