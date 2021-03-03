package com.movienight.app.service.event;

import com.movienight.app.exception.event.EventNotFoundException;
import com.movienight.app.service.utils.mapper.event.EventMapper;
import com.movienight.app.service.user.UserService;
import com.movienight.app.service.utils.validation.type.MethodType;
import com.movienight.app.service.utils.validation.validator.Validator;
import com.movienight.app.service.utils.pageable.PageableService;
import com.movienight.app.service.utils.specification.CustomSpecification;
import com.movienight.app.service.utils.specification.SearchCriteria;
import com.movienight.app.service.utils.specification.SearchOperation;
import com.movienight.foundation.dto.event.EventCreateDto;
import com.movienight.foundation.dto.event.EventFindDto;
import com.movienight.foundation.dto.event.EventOutcomeDto;
import com.movienight.foundation.dto.event.EventUpdateDto;
import com.movienight.foundation.dto.pageable.PageableDto;
import com.movienight.foundation.persistence.model.event.Event;
import com.movienight.foundation.persistence.model.user.User;
import com.movienight.foundation.persistence.repository.EventRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Setter(onMethod_ = @Autowired)
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private EventMapper eventMapper;
    private Validator<Event> validator;
    private UserService userService;

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
    public List<EventOutcomeDto> findAll(EventFindDto finder, PageableDto pageableDto) {
        Pageable pageable = PageableService.getPageable(pageableDto);
        return eventRepository.findAll(createSpecification(finder), pageable).stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long update(Long id, EventUpdateDto dto) {
        Event event = findByIdAndDeletedFalse(id);

        validator.validate(MethodType.UPDATE, dto, event);

        return eventMapper.toEntity(dto, event).getId();
    }

    private Specification<Event> createSpecification(EventFindDto finder) {
        CustomSpecification<Event> customSpecification = new CustomSpecification<>();

        List<SearchCriteria> criteriaList = Arrays.asList(
                new SearchCriteria("name", finder.getName(), SearchOperation.MATCH),
                new SearchCriteria("date", finder.getDate(), SearchOperation.EQUAL),
                new SearchCriteria(
                        "user",
                        finder.getUserId() != null ? userService.findById(finder.getUserId()) : null,
                        SearchOperation.EQUAL
                )
        );

        criteriaList.stream()
                .filter(searchCriteria -> searchCriteria.getValue() != null)
                .forEach(customSpecification::addCriteria);

        return customSpecification;
    }

    private Event findByIdAndDeletedTrue(Long id) {
        return eventRepository.findByIdAndDeletedTrue(id).orElseThrow(EventNotFoundException::new);
    }
}
