package com.kharkiv.movienight.transport.mapper.event;

import com.kharkiv.movienight.persistence.model.event.Event;
import com.kharkiv.movienight.service.actor.ActorService;
import com.kharkiv.movienight.service.movie.MovieService;
import com.kharkiv.movienight.service.user.UserService;
import com.kharkiv.movienight.transport.dto.event.EventCreateDto;
import com.kharkiv.movienight.transport.dto.event.EventOutcomeDto;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

@Mapper(imports = {Instant.class})
@Setter(onMethod_ = @Autowired)
public abstract class EventMapper implements ActorService {

    protected UserService userService;
    protected MovieService movieService;

    @Mapping(target = "movie", expression = "java(movieService.findByIdAndDeletedFalse(dto.getMovieId()))")
    @Mapping(target = "user", expression = "java(getActorFromContext())")
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(Instant.now())")
    @Mapping(target = "createdBy", expression = "java(getActorFromContext())")
    @Mapping(target = "updatedBy", expression = "java(getActorFromContext())")
    public abstract Event toEntity(EventCreateDto dto);

    @Mapping(target = "movieId", source = "event.movie.id")
    @Mapping(target = "userId", source = "event.user.id")
    public abstract EventOutcomeDto toDto(Event event);
}
