package com.movienight.googleserver.persistence.repository;

import com.movienight.googleserver.persistence.domain.GoogleUserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<GoogleUserAccount, String> {
}
