package com.kharkiv.movienight.service;

import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.transport.dto.UserCreateDto;
import com.kharkiv.movienight.transport.dto.UserOutcomeDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;

public interface UserService extends UserDetailsService {

    User findById(Long id);

    UserOutcomeDto getUser(Principal principal);

    Long create(UserCreateDto dto);
}
