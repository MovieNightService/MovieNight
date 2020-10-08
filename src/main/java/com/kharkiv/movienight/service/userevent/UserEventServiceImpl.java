package com.kharkiv.movienight.service.userevent;

import com.kharkiv.movienight.persistence.model.userevent.UserEvent;
import com.kharkiv.movienight.persistence.repository.UserEventRepository;
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

    @Override
    public Long create(UserEventCreateDto dto) {
        UserEvent userEvent = userEventMapper.toEntity(dto);

        userEvent.setUser(getActorFromContext());

        return userEventRepository.save(userEvent).getId();
    }

    @Override
    public List<UserEventOutcomeDto> findAll() {
//        return userEventRepository.findAll().stream()
//                .map(userEventMapper::toOutcomeDto)
//                .collect(Collectors.toList());
        return null; // TODO: 08.10.2020  
    }
}
