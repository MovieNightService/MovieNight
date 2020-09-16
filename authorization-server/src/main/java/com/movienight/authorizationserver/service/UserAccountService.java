package com.movienight.authorizationserver.service;

import com.movienight.authorizationserver.persistence.model.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAccountService extends UserDetailsService {

    UserAccount findByIdUnsafe(Long id);
}
