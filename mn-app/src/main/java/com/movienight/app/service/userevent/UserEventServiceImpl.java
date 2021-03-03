package com.movienight.app.service.userevent;

import com.movienight.app.exception.userevent.UserEventNotFoundException;
import com.movienight.app.service.event.EventService;
import com.movienight.app.service.utils.mapper.userevent.UserEventMapper;
import com.movienight.app.service.user.UserService;
import com.movienight.app.service.utils.actor.RoleUtils;
import com.movienight.app.service.utils.pageable.PageableService;
import com.movienight.app.service.utils.specification.CustomSpecification;
import com.movienight.app.service.utils.specification.SearchCriteria;
import com.movienight.app.service.utils.specification.SearchOperation;
import com.movienight.app.service.ticket.TicketService;
import com.movienight.app.service.utils.validation.type.MethodType;
import com.movienight.app.service.utils.validation.validator.Validator;
import com.movienight.foundation.dto.pageable.PageableDto;
import com.movienight.foundation.dto.userevent.UserEventCreateDto;
import com.movienight.foundation.dto.userevent.UserEventFindDto;
import com.movienight.foundation.dto.userevent.UserEventOutcomeDto;
import com.movienight.foundation.persistence.model.user.User;
import com.movienight.foundation.persistence.model.userevent.UserEvent;
import com.movienight.foundation.persistence.repository.UserEventRepository;
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
    public List<UserEventOutcomeDto> findAll(UserEventFindDto finder, PageableDto pageableDto) {
        Pageable pageable = PageableService.getPageable(pageableDto);
        User actor = getActorFromContext();

        if (RoleUtils.isUser(actor)) {
            finder.setUserId(actor.getId());
        }

        return userEventRepository.findAll(createSpecification(finder), pageable).stream()
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
