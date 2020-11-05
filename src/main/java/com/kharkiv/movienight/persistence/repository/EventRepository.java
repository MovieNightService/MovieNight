package com.kharkiv.movienight.persistence.repository;

import com.kharkiv.movienight.persistence.model.event.Event;
import com.kharkiv.movienight.persistence.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event>, PagingAndSortingRepository<Event, Long> {

    Optional<Event> findByIdAndDeletedFalse(Long id);

    Optional<Event> findByIdAndDeletedTrue(Long id);

    boolean existsByCreatedByAndMovieId(User createdBy, Long movieId);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
