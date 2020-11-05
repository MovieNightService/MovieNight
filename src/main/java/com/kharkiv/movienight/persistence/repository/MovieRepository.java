package com.kharkiv.movienight.persistence.repository;

import com.kharkiv.movienight.persistence.model.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

}
