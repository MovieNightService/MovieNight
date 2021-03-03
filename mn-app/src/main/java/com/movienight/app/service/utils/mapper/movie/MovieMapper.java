package com.movienight.app.service.utils.mapper.movie;

import com.movienight.foundation.dto.movie.MovieCreateDto;
import com.movienight.foundation.dto.movie.MovieImageUploadDto;
import com.movienight.foundation.dto.movie.MovieOutcomeDto;
import com.movienight.foundation.dto.movie.MovieUpdateDto;
import com.movienight.foundation.persistence.model.movie.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.Instant;

@Mapper(imports = {Instant.class})
public abstract class MovieMapper {

    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(Instant.now())")
    public abstract Movie toEntity(MovieCreateDto dto);

    @Mapping(target = "createdBy", source = "movie.createdBy.id")
    @Mapping(target = "updatedBy", source = "movie.updatedBy.id")
    @Mapping(target = "id", source = "movie.id")
    public abstract MovieOutcomeDto toDto(Movie movie);

    public abstract Movie toEntity(MovieUpdateDto dto, @MappingTarget Movie movie);

    public abstract Movie toEntity(MovieImageUploadDto dto, @MappingTarget Movie movie);
}
