package com.kharkiv.movienight.service.userevent;

import com.kharkiv.movienight.exception.userevent.UserEventNotFoundException;
import com.kharkiv.movienight.persistence.model.movie.Movie;
import com.kharkiv.movienight.persistence.model.userevent.UserEvent;
import com.kharkiv.movienight.persistence.repository.UserEventRepository;
import com.kharkiv.movienight.service.event.EventService;
import com.kharkiv.movienight.service.user.UserService;
import com.kharkiv.movienight.service.utils.specification.CustomSpecification;
import com.kharkiv.movienight.service.utils.specification.SearchCriteria;
import com.kharkiv.movienight.service.utils.specification.SearchOperation;
import com.kharkiv.movienight.service.utils.ticket.TicketService;
import com.kharkiv.movienight.service.utils.validation.type.MethodType;
import com.kharkiv.movienight.service.utils.validation.validator.Validator;
import com.kharkiv.movienight.transport.dto.event.EventFindDto;
import com.kharkiv.movienight.transport.dto.userevent.UserEventCreateDto;
import com.kharkiv.movienight.transport.dto.userevent.UserEventFindDto;
import com.kharkiv.movienight.transport.dto.userevent.UserEventOutcomeDto;
import com.kharkiv.movienight.transport.mapper.userevent.UserEventMapper;
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
public class UserEventServiceImpl implements UserEventService {

    private UserEventRepository userEventRepository;
    private UserEventMapper userEventMapper;
    private Validator<UserEvent> validator;
    private TicketService ticketService;
    private UserService userService;
    private EventService eventService;

    @Override
    public Long create(UserEventCreateDto dto) {
        UserEvent userEvent = userEventMapper.toEntity(dto);
        userEvent.setUser(getActorFromContext());

        validator.validate(MethodType.CREATE, dto, userEvent);

        ticketService.create(userEvent);
        return userEventRepository.save(userEvent).getId();
    }

    @Override
    public List<UserEventOutcomeDto> findAll(UserEventFindDto finder) {
        return userEventRepository.findAll(createSpecification(finder)).stream()
                .map(userEventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long delete(Long id) {
        UserEvent userEvent = findById(id);

        validator.validate(MethodType.DELETE, userEvent);

        userEventRepository.delete(userEvent);

        return userEvent.getId();
    }

    private Specification<UserEvent> createSpecification(UserEventFindDto finder){
        CustomSpecification<UserEvent> customSpecification = new CustomSpecification<>();

        List<SearchCriteria> criteriaList = Arrays.asList(
                new SearchCriteria(
                        "user",
                        finder.getUserId() != null ? userService.findByIdAndDeletedFalse(finder.getUserId()) : null,
                        SearchOperation.EQUAL
                ),
                new SearchCriteria(
                        "event",
                        finder.getEventId() != null ? eventService.findByIdAndDeletedFalse(finder.getEventId()) : null,
                        SearchOperation.EQUAL
                )
        );

        criteriaList.stream()
                .filter(searchCriteria -> searchCriteria.getValue() != null)
                .forEach(customSpecification::addCriteria);

        return customSpecification;
    }

    private UserEvent findById(Long id) {
        return userEventRepository.findById(id).orElseThrow(UserEventNotFoundException::new);
    }
}
