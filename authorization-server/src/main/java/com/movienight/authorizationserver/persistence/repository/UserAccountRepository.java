package com.movienight.authorizationserver.persistence.repository;

import com.movienight.authorizationserver.persistence.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    boolean existsByUsername(String username);

    UserAccount findByUsername(String username);
}
