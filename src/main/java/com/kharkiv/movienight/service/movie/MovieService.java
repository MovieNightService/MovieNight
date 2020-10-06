package com.kharkiv.movienight.service.movie;

import com.kharkiv.movienight.persistence.model.movie.Movie;
import com.kharkiv.movienight.service.actor.ActorService;
import com.kharkiv.movienight.transport.dto.movie.MovieCreateDto;

public interface MovieService extends ActorService {

    Movie findByIdAndDeletedFalse(Long movieId);

    Long create(MovieCreateDto dto);
}
