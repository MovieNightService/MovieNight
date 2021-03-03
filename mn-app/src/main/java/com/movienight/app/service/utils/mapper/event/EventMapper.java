package com.movienight.app.service.utils.mapper.event;

import com.movienight.app.service.movie.MovieService;
import com.movienight.app.service.user.UserService;
import com.movienight.app.service.utils.mapper.movie.MovieMapper;
import com.movienight.app.service.utils.mapper.user.UserMapper;
import com.movienight.foundation.dto.event.EventCreateDto;
import com.movienight.foundation.dto.event.EventOutcomeDto;
import com.movienight.foundation.dto.event.EventUpdateDto;
import com.movienight.foundation.persistence.model.event.Event;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

@Mapper(imports = {Instant.class})
@Setter(onMethod_ = @Autowired)
public abstract class EventMapper {

    protected UserService userService;
    protected MovieService movieService;
    protected UserMapper userMapper;
    protected MovieMapper movieMapper;

    @Mapping(target = "movie", expression = "java(movieService.findById(dto.getMovieId()))")
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(Instant.now())")
    public abstract Event toEntity(EventCreateDto dto);

    @Mapping(target = "movie", expression = "java(movieMapper.toDto(event.getMovie()))")
    @Mapping(target = "user", expression = "java(userMapper.toDto(event.getUser()))")
    @Mapping(target = "createdBy", source = "event.createdBy.id")
    @Mapping(target = "updatedBy", source = "event.updatedBy.id")
    @Mapping(target = "id", source = "event.id")
    public abstract EventOutcomeDto toDto(Event event);

    public abstract Event toEntity(EventUpdateDto dto, @MappingTarget Event event);
}
