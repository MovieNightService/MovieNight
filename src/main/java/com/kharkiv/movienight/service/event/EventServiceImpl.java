package com.kharkiv.movienight.service.event;

import com.kharkiv.movienight.exception.event.EventNotFoundException;
import com.kharkiv.movienight.persistence.model.event.Event;
import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.repository.EventRepository;
import com.kharkiv.movienight.service.validation.type.MethodType;
import com.kharkiv.movienight.service.validation.validator.Validator;
import com.kharkiv.movienight.transport.dto.event.EventCreateDto;
import com.kharkiv.movienight.transport.dto.event.EventOutcomeDto;
import com.kharkiv.movienight.transport.dto.event.EventUpdateDto;
import com.kharkiv.movienight.transport.mapper.event.EventMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Setter(onMethod_ = @Autowired)
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private EventMapper eventMapper;
    private Validator<Event> validator;

    @Override
    public Long create(EventCreateDto dto) {
        User actor = getActorFromContext();
        Event event = eventMapper.toEntity(dto);

        validator.validate(MethodType.CREATE, dto, actor);

        event.setCreatedBy(actor);
        event.setUpdatedBy(actor);
        event.setUser(actor);

        return eventRepository.save(event).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Event findByIdAndDeletedFalse(Long id) {
        return eventRepository.findByIdAndDeletedFalse(id).orElseThrow(EventNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public EventOutcomeDto findById(Long id) {
        return eventMapper.toDto(eventRepository.findByIdAndDeletedFalse(id).orElseThrow(EventNotFoundException::new));
    }

    @Override
    public Long delete(Long id) {
        Event event = findByIdAndDeletedFalse(id);

        validator.validate(MethodType.DELETE, event);

        event.setDeleted(true);
        return event.getId();
    }

    @Override
    public Long restore(Long id) {
        Event event = findByIdAndDeletedTrue(id);

        validator.validate(MethodType.RESTORE, event);

        event.setDeleted(false);
        return event.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventOutcomeDto> findAll() {
        return eventRepository.findAll().stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long update(Long id, EventUpdateDto dto) {
        Event event = findByIdAndDeletedFalse(id);

        validator.validate(MethodType.UPDATE, dto, event);

        return eventMapper.toEntity(dto, event).getId();
    }

    private Event findByIdAndDeletedTrue(Long id) {
        return eventRepository.findByIdAndDeletedTrue(id).orElseThrow(EventNotFoundException::new);
    }
}
