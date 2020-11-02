package com.kharkiv.movienight.service.event;

import com.kharkiv.movienight.exception.event.EventNotFoundException;
import com.kharkiv.movienight.persistence.model.event.Event;
import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.repository.EventRepository;
import com.kharkiv.movienight.service.event.specification.EventSpecification;
import com.kharkiv.movienight.service.user.UserService;
import com.kharkiv.movienight.service.utils.specification.SearchCriteria;
import com.kharkiv.movienight.service.utils.specification.SearchOperation;
import com.kharkiv.movienight.service.utils.validation.type.MethodType;
import com.kharkiv.movienight.service.utils.validation.validator.Validator;
import com.kharkiv.movienight.transport.dto.event.EventCreateDto;
import com.kharkiv.movienight.transport.dto.event.EventFindDto;
import com.kharkiv.movienight.transport.dto.event.EventOutcomeDto;
import com.kharkiv.movienight.transport.dto.event.EventUpdateDto;
import com.kharkiv.movienight.transport.mapper.event.EventMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<EventOutcomeDto> findAll(EventFindDto finder) {
        return eventRepository.findAll(createSpecification(finder)).stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long update(Long id, EventUpdateDto dto) {
        Event event = findByIdAndDeletedFalse(id);

        validator.validate(MethodType.UPDATE, dto, event);

        return eventMapper.toEntity(dto, event).getId();
    }

    private Specification<Event> createSpecification(EventFindDto finder){
        EventSpecification eventSpecification = new EventSpecification();

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
                .forEach(eventSpecification::addCriteria);

        return eventSpecification;
    }

    private Event findByIdAndDeletedTrue(Long id) {
        return eventRepository.findByIdAndDeletedTrue(id).orElseThrow(EventNotFoundException::new);
    }
}
