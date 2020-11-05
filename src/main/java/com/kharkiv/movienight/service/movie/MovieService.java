package com.kharkiv.movienight.service.movie;

import com.kharkiv.movienight.persistence.model.movie.Movie;
import com.kharkiv.movienight.service.utils.actor.ActorService;
import com.kharkiv.movienight.transport.dto.movie.MovieCreateDto;
import com.kharkiv.movienight.transport.dto.movie.MovieFindDto;
import com.kharkiv.movienight.transport.dto.movie.MovieOutcomeDto;
import com.kharkiv.movienight.transport.dto.movie.MovieUpdateDto;

import java.util.List;

public interface MovieService extends ActorService {

    Long create(MovieCreateDto dto);

    Movie findById(Long id);

    MovieOutcomeDto findByIdUnsafe(Long id);

    void delete(Long id);

    List<MovieOutcomeDto> findAll(MovieFindDto finder);

    Long update(Long id, MovieUpdateDto dto);
}
