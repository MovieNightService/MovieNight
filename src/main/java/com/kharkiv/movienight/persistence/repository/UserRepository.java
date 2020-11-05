package com.kharkiv.movienight.persistence.repository;

import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.model.userevent.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsernameAndDeletedFalse(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByUsername(String username);

    boolean existsByIdAndDeletedFalse(Long id);

    Optional<User> findByIdAndDeletedFalse(Long id);

    Optional<User> findByIdAndDeletedTrue(Long id);
}
