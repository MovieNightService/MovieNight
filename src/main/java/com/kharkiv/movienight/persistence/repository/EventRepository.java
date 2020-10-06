package com.kharkiv.movienight.persistence.repository;

import com.kharkiv.movienight.persistence.model.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

}
