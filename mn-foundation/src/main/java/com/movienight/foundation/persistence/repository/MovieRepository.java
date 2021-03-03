package com.movienight.foundation.persistence.repository;

import com.movienight.foundation.persistence.model.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

}
