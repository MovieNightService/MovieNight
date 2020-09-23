package com.kharkiv.movienight.service;

import com.kharkiv.movienight.exception.PasswordMismatchException;
import com.kharkiv.movienight.exception.UserNotFoundException;
import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.repository.UserRepository;
import com.kharkiv.movienight.transport.dto.UserCreateDto;
import com.kharkiv.movienight.transport.dto.UserOutcomeDto;
import com.kharkiv.movienight.transport.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.Instant;
import java.util.Collections;

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
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserOutcomeDto getUser(Principal principal) {
        User user = findUserByEmail(principal.getName());
        return userMapper.toOutcomeDto(user);
    }

    @Override
    public Long create(UserCreateDto dto) {
        User user = userMapper.toEntity(dto);

        user.setAgreement(true);
        user.setAuthorities(Collections.singletonList(dto.getRole()));
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        user.setDeleted(false);

        if(dto.getConfirmPassword().equals(dto.getPassword())){
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        } else {
            throw new PasswordMismatchException();
        }

        return userRepository.save(user).getId();
    }

    private User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}