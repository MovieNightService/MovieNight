package com.kharkiv.movienight.persistence.repository;

import com.kharkiv.movienight.persistence.model.event.Event;
import com.kharkiv.movienight.persistence.model.movie.Movie;
import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.model.userevent.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserEventRepository extends JpaRepository<UserEvent, Long>, JpaSpecificationExecutor<UserEvent> {

    boolean existsByEventAndUser(Event event, User user);
}
