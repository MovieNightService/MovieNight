package com.movienight.app.service.movie;

import com.movienight.app.service.utils.actor.ActorService;
import com.movienight.foundation.dto.movie.*;
import com.movienight.foundation.dto.pageable.PageableDto;
import com.movienight.foundation.persistence.model.movie.Movie;

import java.util.List;

public interface MovieService extends ActorService {

    Long create(MovieCreateDto dto);

    Movie findById(Long id);

    MovieOutcomeDto findByIdUnsafe(Long id);

    void delete(Long id);

    List<MovieOutcomeDto> findAll(MovieFindDto finder, PageableDto pageable);

    Long update(Long id, MovieUpdateDto dto);

    void uploadImage(Long id, MovieImageUploadDto dto);
}
