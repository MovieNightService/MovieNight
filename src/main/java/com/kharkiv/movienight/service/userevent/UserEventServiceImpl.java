package com.kharkiv.movienight.service.userevent;

import com.kharkiv.movienight.exception.userevent.UserEventNotFoundException;
import com.kharkiv.movienight.persistence.model.userevent.UserEvent;
import com.kharkiv.movienight.persistence.repository.UserEventRepository;
import com.kharkiv.movienight.service.ticket.TicketService;
import com.kharkiv.movienight.service.validation.type.MethodType;
import com.kharkiv.movienight.service.validation.validator.Validator;
import com.kharkiv.movienight.transport.dto.userevent.UserEventCreateDto;
import com.kharkiv.movienight.transport.dto.userevent.UserEventOutcomeDto;
import com.kharkiv.movienight.transport.mapper.userevent.UserEventMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public Long create(UserEventCreateDto dto) {
        UserEvent userEvent = userEventMapper.toEntity(dto);
        userEvent.setUser(getActorFromContext());

        validator.validate(MethodType.CREATE, dto, userEvent);

        ticketService.create(userEvent);
        return userEventRepository.save(userEvent).getId();
    }

    @Override
    public List<UserEventOutcomeDto> findAll() {
        return userEventRepository.findAll().stream()
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

    private UserEvent findById(Long id) {
        return userEventRepository.findById(id).orElseThrow(UserEventNotFoundException::new);
    }
}
