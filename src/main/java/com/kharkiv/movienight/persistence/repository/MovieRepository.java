package com.kharkiv.movienight.persistence.repository;

import com.kharkiv.movienight.persistence.model.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
