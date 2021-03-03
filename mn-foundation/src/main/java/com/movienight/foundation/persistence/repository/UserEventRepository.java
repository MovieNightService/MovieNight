package com.movienight.foundation.persistence.repository;

import com.movienight.foundation.persistence.model.event.Event;
import com.movienight.foundation.persistence.model.user.User;
import com.movienight.foundation.persistence.model.userevent.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserEventRepository extends JpaRepository<UserEvent, Long>, JpaSpecificationExecutor<UserEvent> {

    boolean existsByEventAndUser(Event event, User user);
}
