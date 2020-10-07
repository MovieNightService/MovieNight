package com.kharkiv.movienight.transport.mapper.userevent;

import com.kharkiv.movienight.persistence.model.userevent.UserEvent;
import com.kharkiv.movienight.service.actor.ActorService;
import com.kharkiv.movienight.service.event.EventService;
import com.kharkiv.movienight.service.user.UserService;
import com.kharkiv.movienight.transport.dto.userevent.UserEventCreateDto;
import com.kharkiv.movienight.transport.dto.userevent.UserEventOutcomeDto;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
@Setter(onMethod_ = @Autowired)
public abstract class UserEventMapper implements ActorService {

    protected EventService eventService;

    @Mapping(target = "event", expression = "java(eventService.findByIdAndDeletedFalse(dto.getEventId()))")
    @Mapping(target = "user", expression = "java(getActorFromContext())")
    public abstract UserEvent toEntity(UserEventCreateDto dto);

    @Mapping(target = "user.createdBy", source = "user.createdBy.id")
    @Mapping(target = "user.updatedBy", source = "user.updatedBy.id")
    public abstract UserEventOutcomeDto toOutcomeDto(UserEvent userEvent);
}
