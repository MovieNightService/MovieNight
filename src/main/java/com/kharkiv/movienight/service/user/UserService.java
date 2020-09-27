package com.kharkiv.movienight.service.user;

import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.service.actor.ActorService;
import com.kharkiv.movienight.transport.dto.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;
import java.util.List;

public interface UserService extends UserDetailsService, ActorService {

    UserOutcomeDto getUser(Principal principal);

    Long registration(UserCreateDto dto);

    Long update(Long id, UserUpdateDto dto);

    Long delete(Long id);

    Long restore(Long id);

    List<UserOutcomeDto> findAll();

    void agreement(UserAgreementDto dto);

    void changeRole(UserRoleDto dto);

    User findById(Long id);

    Long update(UserUpdateEmailDto dto);

    boolean existByEmail(String email);
}
