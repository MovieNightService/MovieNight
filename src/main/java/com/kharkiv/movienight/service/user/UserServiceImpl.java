package com.kharkiv.movienight.service.user;

import com.kharkiv.movienight.exception.PasswordMismatchException;
import com.kharkiv.movienight.exception.UserNotFoundException;
import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.model.user.UserRole;
import com.kharkiv.movienight.persistence.repository.UserRepository;
import com.kharkiv.movienight.service.user.UserService;
import com.kharkiv.movienight.transport.dto.*;
import com.kharkiv.movienight.transport.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserOutcomeDto getUser(Principal principal) {
        User user = findUserByEmail(principal.getName());
        return userMapper.toOutcomeDto(user);
    }

    @Override
    public Long registration(UserCreateDto dto) {
        User user = userMapper.toEntity(dto);

        if(dto.getConfirmPassword().equals(dto.getPassword())){
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        } else {
            throw new PasswordMismatchException();
        }

        user.setCreatedBy(getActorFromContext());
        user.setUpdatedBy(getActorFromContext());
        user.setAuthorities(Collections.singletonList(UserRole.USER));

        return userRepository.save(user).getId();
    }

    @Override
    public Long update(Long id, UserUpdateDto dto) {
        User user = findById(id);

        return userMapper.toEntity(dto, user).getId();
    }

    @Override
    public Long delete(Long id) {
        User user = findById(id);
        user.setDeleted(true);
        return user.getId();
    }

    @Override
    public Long restore(Long id) {
        User user = findById(id);
        user.setDeleted(false);
        return user.getId();
    }

    @Override
    public List<UserOutcomeDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toOutcomeDto)
                .collect(Collectors.toList());
    }

    @Override
    public void agreement(UserAgreementDto dto) {
        User user = findById(dto.getUserId());
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

    private User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}