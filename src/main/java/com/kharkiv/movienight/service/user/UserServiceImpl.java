package com.kharkiv.movienight.service.user;

import com.kharkiv.movienight.exception.user.*;
import com.kharkiv.movienight.exception.standard.BadRequestException;
import com.kharkiv.movienight.exception.user.PasswordMismatchException;
import com.kharkiv.movienight.exception.user.UserBadCredentialsException;
import com.kharkiv.movienight.exception.user.UserNotFoundException;
import com.kharkiv.movienight.exception.user.UserWithSuchEmailAlreadyExistsException;
import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.model.user.UserRole;
import com.kharkiv.movienight.persistence.repository.UserRepository;
import com.kharkiv.movienight.service.validation.type.MethodType;
import com.kharkiv.movienight.service.validation.validator.Validator;
import com.kharkiv.movienight.service.validation.validator.provider.ValidatorProvider;
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
        ValidatorProvider.getValidator(ModelType.USER).validate(MethodType.FIND_ALL);
        return userRepository.findAll().stream()
                .map(userMapper::toOutcomeDto)
                .collect(Collectors.toList());
    }

    @Override
    public void agreement(UserAgreementDto dto) {
        User user = findById(getActorFromContext().getId());
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
        User actor = findById(getActorFromContext().getId());
        validateUpdatingEmail(actor, dto);
        actor.setEmail(dto.getNewEmail());
        return actor.getId();
    }

    @Override
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
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
            throw new UserWithSuchEmailAlreadyExistsException();
        }
    }

    private void validatePhone(String phone) {
        if(userRepository.existsByPhone(phone)){
            throw new UserWithSuchPhoneAlreadyExistsException();
        }
    }

    private void validateUsername(String username) {
        if(userRepository.existsByUsername(username)){
            throw new UserWithSuchUserNameAlreadyExistsException();
        }
    }

    private void validateDateOfBirth(Instant dateOfBirth) {
        Instant minDate = LocalDate.parse("2008-01-01").atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant maxDate = LocalDate.parse("1920-01-01").atStartOfDay(ZoneId.systemDefault()).toInstant();

        int minDateResult = dateOfBirth.compareTo(minDate);
        int maxDateResult = dateOfBirth.compareTo(maxDate);

        boolean isValidDateOfBirth = (minDateResult < 0 && maxDateResult > 0);

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

    /* update email validate */
    private void validateUpdatingEmail(User actor, UserUpdateEmailDto dto) {

        validateOldAndNewEmailNotEqual(dto);
        validateTruePassword(actor, dto);
        validateOldEmail(actor, dto);
        validateUniqueEmail(dto.getNewEmail());
    }

    private void validateOldAndNewEmailNotEqual(UserUpdateEmailDto dto) {
        if(dto.getNewEmail().equals(dto.getOldEmail())){
            throw new BadRequestException("New email must be different from old");
        }
    }

    private void validateTruePassword(User actor, UserUpdateEmailDto dto) {
        if(!passwordEncoder.matches(dto.getPassword(), actor.getPassword())){
            throw new UserBadCredentialsException();
        }
    }

    private void validateOldEmail(User actor, UserUpdateEmailDto dto) {
        if(!dto.getOldEmail().equals(actor.getEmail())){
            throw new UserBadCredentialsException();
        }
    }

    private void validateUniqueEmail(String newEmail) {
        if(existByEmail(newEmail)){
            throw new UserWithSuchEmailAlreadyExistsException();
        }
    }
    /* end update email validate */
}
