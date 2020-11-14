package com.kharkiv.movienight.transport.mapper.userevent;

import com.kharkiv.movienight.persistence.model.userevent.UserEvent;
import com.kharkiv.movienight.service.event.EventService;
import com.kharkiv.movienight.transport.dto.userevent.UserEventCreateDto;
import com.kharkiv.movienight.transport.dto.userevent.UserEventOutcomeDto;
import com.kharkiv.movienight.transport.mapper.event.EventMapper;
import com.kharkiv.movienight.transport.mapper.user.UserMapper;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
@Setter(onMethod_ = @Autowired)
public abstract class UserEventMapper {

    protected EventService eventService;
    protected UserMapper userMapper;
    protected EventMapper eventMapper;

    @Mapping(target = "event", expression = "java(eventService.findByIdAndDeletedFalse(dto.getEventId()))")
    public abstract UserEvent toEntity(UserEventCreateDto dto);

    @Mapping(target = "event", expression = "java(eventMapper.toDto(userEvent.getEvent()))")
    @Mapping(target = "user", expression = "java(userMapper.toDto(userEvent.getUser()))")
    @Mapping(target = "id", source = "userEvent.id")
    public abstract UserEventOutcomeDto toDto(UserEvent userEvent);
}
