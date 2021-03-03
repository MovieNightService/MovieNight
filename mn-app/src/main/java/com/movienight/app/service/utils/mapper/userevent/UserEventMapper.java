package com.movienight.app.service.utils.mapper.userevent;

import com.movienight.app.service.event.EventService;
import com.movienight.app.service.utils.mapper.event.EventMapper;
import com.movienight.app.service.utils.mapper.user.UserMapper;
import com.movienight.foundation.dto.userevent.UserEventCreateDto;
import com.movienight.foundation.dto.userevent.UserEventOutcomeDto;
import com.movienight.foundation.persistence.model.userevent.UserEvent;
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
