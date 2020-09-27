package com.kharkiv.movienight.persistence.repository;

import com.kharkiv.movienight.persistence.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    User findByEmail(String email);

    boolean existsByEmail(String email);
}
