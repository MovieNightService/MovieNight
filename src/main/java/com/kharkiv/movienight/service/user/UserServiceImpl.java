package com.kharkiv.movienight.service.user;

import com.kharkiv.movienight.exception.user.UploadAvatarException;
import com.kharkiv.movienight.exception.user.UserNotFoundException;
import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.model.user.UserRole;
import com.kharkiv.movienight.persistence.repository.UserRepository;
import com.kharkiv.movienight.service.validation.type.MethodType;
import com.kharkiv.movienight.service.validation.validator.Validator;
import com.kharkiv.movienight.transport.dto.user.*;
import com.kharkiv.movienight.transport.mapper.user.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final Validator<User> validator;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, Validator<User> validator) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsernameAndDeletedFalse(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserOutcomeDto getUser(Principal principal) {
        User user = findByUsernameAndDeletedFalse(principal.getName());
        return userMapper.toDto(user);
    }

    @Override
    public Long registration(UserRegistrationDto dto) {
        User user = userMapper.toEntity(dto);

        validator.validate(MethodType.REGISTRATION, dto, user);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setAuthorities(Collections.singletonList(UserRole.USER));

        return userRepository.save(user).getId();
    }

    @Override
    public Long update(Long id, UserUpdateDto dto) {
        User user = findById(id);

        validator.validate(MethodType.UPDATE, dto, user);

        return userMapper.toEntity(dto, user).getId();
    }

    @Override
    public Long delete(Long id) {
        User user = findByIdAndDeletedFalse(id);

        validator.validate(MethodType.DELETE, user);

        user.setDeleted(true);
        return user.getId();
    }

    @Override
    public Long restore(Long id) {
        User user = findByIdAndDeletedTrue(id);

        validator.validate(MethodType.RESTORE, user);

        user.setDeleted(false);
        return user.getId();
    }

    @Override
    public List<UserOutcomeDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void agreement(UserAgreementDto dto) {
        User user = findById(dto.getUserId());

        validator.validate(MethodType.AGREEMENT, user);

        user.setAgreement(dto.isAgreement());
    }

    @Override
    public void changeRole(UserRoleDto dto) {
        User user = findById(dto.getUserId());
        user.setAuthorities(Collections.singletonList(dto.getRole()));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Long update(UserUpdateEmailDto dto) {
        User user = findById(getActorFromContext().getId());

        validator.validate(MethodType.UPDATE_EMAIL, dto, user);

        user.setEmail(dto.getNewEmail());
        return user.getId();
    }

    @Override
    public Long update(UserResetPasswordDto dto) {
        User actor = findById(getActorFromContext().getId());

        validator.validate(MethodType.RESET_PASSWORD, dto, actor);

        actor.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        return userRepository.save(actor).getId();
    }

    @Override
    public Long uploadAvatar(MultipartFile avatar) {
        User actor = findById(getActorFromContext().getId());

        validator.validate(MethodType.UPLOAD_AVATAR, avatar, actor);

        try {
            actor.setAvatar(avatar.getBytes());
        } catch (IOException e) {
            throw new UploadAvatarException();
        }

        return userRepository.save(actor).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public User findByIdAndDeletedFalse(Long id) {
        return userRepository.findByIdAndDeletedFalse(id).orElseThrow(UserNotFoundException::new);
    }

    private User findByIdAndDeletedTrue(Long id) {
        return userRepository.findByIdAndDeletedTrue(id).orElseThrow(UserNotFoundException::new);
    }

    private User findByUsernameAndDeletedFalse(String name) {
        return userRepository.findByUsernameAndDeletedFalse(name).orElseThrow(UserNotFoundException::new);
    }
}
