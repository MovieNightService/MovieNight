package com.kharkiv.movienight.service.userevent;

import com.kharkiv.movienight.persistence.model.userevent.UserEvent;
import com.kharkiv.movienight.persistence.repository.UserEventRepository;
import com.kharkiv.movienight.transport.dto.userevent.UserEventCreateDto;
import com.kharkiv.movienight.transport.mapper.userevent.UserEventMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Setter(onMethod_ = @Autowired)
public class UserEventServiceImpl implements UserEventService {

    private UserEventRepository userEventRepository;
    private UserEventMapper userEventMapper;

    @Override
    public Long create(UserEventCreateDto dto) {
        UserEvent userEvent = userEventMapper.toEntity(dto);
        return userEventRepository.save(userEvent).getId();
    }
}
