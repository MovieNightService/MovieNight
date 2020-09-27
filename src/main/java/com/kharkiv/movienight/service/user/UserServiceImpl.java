package com.kharkiv.movienight.service.user;

import com.kharkiv.movienight.exception.user.*;
import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.model.user.UserRole;
import com.kharkiv.movienight.persistence.repository.UserRepository;
import com.kharkiv.movienight.service.validation.type.MethodType;
import com.kharkiv.movienight.service.validation.validator.provider.AccessValidatorProvider;
import com.kharkiv.movienight.service.validation.type.ModelType;
import com.kharkiv.movienight.transport.dto.*;
import com.kharkiv.movienight.transport.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
    public Long registration(UserRegistrationDto dto) {
        User user = userMapper.toEntity(dto);

        validateRegistration(dto);

        if(isPasswordConfirmed(dto)){
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
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
        AccessValidatorProvider.getValidator(ModelType.USER).validate(MethodType.FIND_ALL);
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

    private void validateRegistration(UserRegistrationDto dto) {
        validateDateOfBirth(dto.getDateOfBirth());
        validateUsername(dto.getUsername());
        validatePhone(dto.getPhone());
        validateEmail(dto.getEmail());
    }

    private void validateEmail(String email) {
        if(userRepository.existsByEmail(email)){
            throw new UserWithSuchEmailAlreadyExists();
        }
    }

    private void validatePhone(String phone) {
        if(userRepository.existsByPhone(phone)){
            throw new UserWithSuchPhoneAlreadyExists();
        }
    }

    private void validateUsername(String username) {
        if(userRepository.existsByUsername(username)){
            throw new UserWithSuchUserNameAlreadyExists();
        }
    }

    private void validateDateOfBirth(Instant dateOfBirth) {
        Instant minDate = LocalDate.parse("2008-01-01").atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant maxDate = LocalDate.parse("1920-01-01").atStartOfDay(ZoneId.systemDefault()).toInstant();

        int minDateResult = dateOfBirth.compareTo(minDate);
        int maxDateResult = dateOfBirth.compareTo(maxDate);

        boolean isValidDateOfBirth = (minDateResult < 0 && maxDateResult > 0 || minDateResult == 0);

        if(!isValidDateOfBirth) {
            throw new UserDateOfBirthInvalidException();
        }
    }

    private boolean isPasswordConfirmed(UserRegistrationDto dto){
        if(dto.getConfirmPassword().equals(dto.getPassword())){
            return true;
        } else {
            throw new PasswordMismatchException();
        }
    }
}