package com.movienight.app.service.user;

import com.movienight.app.service.utils.actor.ActorService;
import com.movienight.foundation.dto.pageable.PageableDto;
import com.movienight.foundation.dto.user.*;
import com.movienight.foundation.persistence.model.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

public interface UserService extends UserDetailsService, ActorService {

    UserOutcomeDto getUser(Principal principal);

    Long registration(UserRegistrationDto dto);

    Long update(Long id, UserUpdateDto dto);

    Long delete(Long id);

    Long restore(Long id);

    List<UserOutcomeDto> findAll(UserFindDto finder, PageableDto pageableDto);

    void agreement(UserAgreementDto dto);

    void changeRole(UserRoleDto dto);

    User findById(Long id);

    Long update(UserUpdateEmailDto dto);

    Long update(UserResetPasswordDto dto);

    Long uploadAvatar(MultipartFile avatar);

    User findByIdAndDeletedFalse(Long id);
}
