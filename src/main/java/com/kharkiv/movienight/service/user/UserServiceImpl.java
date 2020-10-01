package com.kharkiv.movienight.service.user;

import com.kharkiv.movienight.exception.user.*;
import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.model.user.UserRole;
import com.kharkiv.movienight.persistence.repository.UserRepository;
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
import java.util.regex.Pattern;
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
        User user = findByUsername(principal.getName());
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
    public Long update(UserResetPasswordDto dto) {
        User actor = findById(getActorFromContext().getId());
        validateUpdatingPassword(actor, dto);
        validatePasswordPattern(dto);
        actor.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        return userRepository.save(actor).getId();
    }

    @Override
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private User findByUsername(String name) {
        return userRepository.findByUsername(name).orElseThrow(UserNotFoundException::new);
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

    private void validateUpdatingEmail(User actor, UserUpdateEmailDto dto) {
        validateOldEmail(actor, dto);
        validateAccess(actor, dto);
        validateNewEmail(dto);
    }

    private void validateNewEmail(UserUpdateEmailDto dto) {
        if(dto.getNewEmail().equals(dto.getOldEmail())){
            throw new UserWithSuchEmailAlreadyExistsException("New email must be different from old");
        }
        if(existByEmail(dto.getNewEmail())){
            throw new UserWithSuchEmailAlreadyExistsException();
        }
    }

    private void validateAccess(User actor, UserUpdateEmailDto dto) {
        if(!passwordEncoder.matches(dto.getPassword(), actor.getPassword())){
            throw new UserBadCredentialsException("Invalid password");
        }
    }

    private void validateOldEmail(User actor, UserUpdateEmailDto dto) {
        if(!dto.getOldEmail().equals(actor.getEmail())){
            throw new UserBadCredentialsException("Invalid email");
        }
    }

    private void validateUpdatingPassword(User actor, UserResetPasswordDto dto) {
        if(!passwordEncoder.matches(dto.getOldPassword(), actor.getPassword())){
            throw new UserBadCredentialsException("Invalid password");
        }

        if(actor.getPassword().equals(dto.getNewPassword())){
            throw new UserBadCredentialsException("New password must be different from old");
        }

        if(!dto.getNewPassword().equals(dto.getConfirmPassword())){
            throw new PasswordMismatchException();
        }
    }

    private void validatePasswordPattern(UserResetPasswordDto dto) {
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");

        if (dto.getNewPassword().length() < 4) {
            throw new UserBadCredentialsException("Password lenght must have at least 4 character");
        }

        if (!UpperCasePatten.matcher(dto.getNewPassword()).find()) {
            throw new UserBadCredentialsException("Password must have at least one uppercase character");
        }

        if (!lowerCasePatten.matcher(dto.getNewPassword()).find()) {
            throw new UserBadCredentialsException("Password must have at least one lowercase character");
        }
    }
}
