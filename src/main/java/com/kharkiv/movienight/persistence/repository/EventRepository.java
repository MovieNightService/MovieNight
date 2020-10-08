package com.kharkiv.movienight.persistence.repository;

import com.kharkiv.movienight.persistence.model.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByIdAndDeletedFalse(Long id);

    Optional<Event> findByIdAndDeletedTrue(Long id);
}
