package com.kharkiv.movienight.persistence.repository;

import com.kharkiv.movienight.persistence.model.event.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEventRepository extends JpaRepository<UserEvent, Long> {

}
