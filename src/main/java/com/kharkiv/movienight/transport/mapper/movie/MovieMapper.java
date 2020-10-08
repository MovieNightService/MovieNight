package com.kharkiv.movienight.transport.mapper.movie;

import com.kharkiv.movienight.persistence.model.movie.Movie;
import com.kharkiv.movienight.transport.dto.movie.MovieCreateDto;
import com.kharkiv.movienight.transport.dto.movie.MovieOutcomeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(imports = {Instant.class})
public abstract class MovieMapper {

    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(Instant.now())")
    public abstract Movie toEntity(MovieCreateDto dto);

    @Mapping(target = "createdBy", source = "movie.createdBy.id")
    @Mapping(target = "updatedBy", source = "movie.updatedBy.id")
    public abstract MovieOutcomeDto toDto(Movie movie);
}
