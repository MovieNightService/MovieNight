package com.kharkiv.movienight.service.validation.model;

import com.kharkiv.movienight.exception.standard.BadRequestException;
import com.kharkiv.movienight.exception.standard.ForbiddenException;
import com.kharkiv.movienight.exception.user.*;
import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.repository.UserRepository;
import com.kharkiv.movienight.service.validation.type.MethodType;
import com.kharkiv.movienight.service.validation.validator.Validator;
import com.kharkiv.movienight.transport.dto.UserRegistrationDto;
import com.kharkiv.movienight.transport.dto.UserResetPasswordDto;
import com.kharkiv.movienight.transport.dto.UserUpdateDto;
import com.kharkiv.movienight.transport.dto.UserUpdateEmailDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.regex.Pattern;

@Service
@Setter(onMethod_ = @Autowired)
public class UserValidatorImpl implements Validator<User> {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void validate(MethodType methodType, Object... objects) {
        User actor = getActorFromContext();
        Object dto;
        Object object;
        User user = null;

        if (objects.length == 1) {
            object = objects[0];

            if (object instanceof User) {
                user = (User) object;
            }
            executeValidation(methodType, actor, null, user);

        } else if (objects.length == 2) {
            dto = objects[0];
            object = objects[1];

            if (object instanceof User) {
                user = (User) object;
            }
            executeValidation(methodType, actor, dto, user);

        } else {
            executeValidation(methodType, actor, null, null);
        }
    }

    public void executeValidation(MethodType methodType, User actor, Object dto, User user) {
        switch (methodType) {
            case REGISTRATION:
                validateRegistration(dto);
                break;
            case UPDATE:
                validateUpdate(actor, dto, user);
                break;
            case DELETE:
                validateDelete(actor, user);
                break;
            case RESTORE:
                validateRestore(actor, user);
                break;
            case FIND_ALL:
                validateFindAll(actor);
                break;
            case CHANGE_ROLE:
                validateChangeRole(actor);
                break;
            case UPDATE_EMAIL:
                validateUpdateEmail(actor, dto, user);
                break;
            case RESET_PASSWORD:
                validateResetPassword(actor, dto);
                break;
            default:
                throw new BadRequestException("Incorrect METHOD_TYPE");
        }
    }

    private void validateResetPassword(User actor, Object dto) {
        UserResetPasswordDto resetPasswordDto = null;

        if (dto instanceof UserResetPasswordDto) {
            resetPasswordDto = (UserResetPasswordDto) dto;
        }

        if (resetPasswordDto != null) {
            validateUpdatingPassword(actor, resetPasswordDto);
            validatePasswordPattern(resetPasswordDto);
        } else {
            throw new BadRequestException("DTO was not get or his type is incorrect");
        }
    }

    public void validateChangeRole(User actor) {
        validateUserNotDeleted(actor);
    }

    public void validateFindAll(User actor) {
        validateUserNotDeleted(actor);
    }

    public void validateDelete(User actor, User user) {
        validateUserNotDeleted(actor);
        if (!actor.getId().equals(user.getId())) {
            throw new ForbiddenException("You can't delete another user");
        }
    }

    public void validateRestore(User actor, User user) {
        if (!actor.getId().equals(user.getId())) {
            throw new ForbiddenException("You can't restore another user");
        }
    }

    public void validateUpdate(User actor, Object dto, User user) {
        validateUserNotDeleted(actor);

        UserUpdateDto updateDto = null;

        if (dto instanceof UserUpdateDto) {
            updateDto = (UserUpdateDto) dto;
        }

        if (updateDto != null) {
            validateDateOfBirth(updateDto.getDateOfBirth());
            validateUsername(updateDto.getUsername());
            validatePhone(updateDto.getPhone());
        } else {
            throw new BadRequestException("DTO was not get or his type is incorrect");
        }

        if (!actor.getId().equals(user.getId())) {
            throw new ForbiddenException("You can't update another user");
        }
    }

    private void validateUpdateEmail(User actor, Object dto, User user) {
        validateUserNotDeleted(actor);
        if (!actor.getId().equals(user.getId())) {
            throw new ForbiddenException("You can't update email another user");
        }

        UserUpdateEmailDto updateEmailDto = null;

        if (dto instanceof UserUpdateEmailDto) {
            updateEmailDto = (UserUpdateEmailDto) dto;
        }

        if (updateEmailDto != null) {
            validateOldEmail(actor, updateEmailDto);
            validateAccess(actor, updateEmailDto);
            validateNewEmail(updateEmailDto);
        } else {
            throw new BadRequestException("DTO was not get or his type is incorrect");
        }
    }

    public void validateRegistration(Object dto) {
        UserRegistrationDto registrationDto = null;

        if (dto instanceof UserRegistrationDto) {
            registrationDto = (UserRegistrationDto) dto;
        }

        if (registrationDto != null) {
            validateDateOfBirth(registrationDto.getDateOfBirth());
            validateUsername(registrationDto.getUsername());
            validatePhone(registrationDto.getPhone());
            validateEmail(registrationDto.getEmail());
            validatePasswordConfirmed(registrationDto);
        } else {
            throw new BadRequestException("DTO was not get or his type is incorrect");
        }
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserWithSuchEmailAlreadyExistsException();
        }
    }

    private void validatePhone(String phone) {
        if (userRepository.existsByPhone(phone)) {
            throw new UserWithSuchPhoneAlreadyExistsException();
        }
    }

    private void validateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UserWithSuchUserNameAlreadyExistsException();
        }
    }

    private void validateDateOfBirth(Instant dateOfBirth) {
        Instant minDate = LocalDate.parse("2008-01-01").atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant maxDate = LocalDate.parse("1920-01-01").atStartOfDay(ZoneId.systemDefault()).toInstant();

        int minDateResult = dateOfBirth.compareTo(minDate);
        int maxDateResult = dateOfBirth.compareTo(maxDate);

        boolean isValidDateOfBirth = (minDateResult < 0 && maxDateResult > 0);

        if (!isValidDateOfBirth) {
            throw new UserDateOfBirthInvalidException();
        }
    }

    private void validateNewEmail(UserUpdateEmailDto dto) {
        if(dto.getNewEmail().equals(dto.getOldEmail())){
            throw new UserWithSuchEmailAlreadyExistsException("New email must be different from old");
        }
        if(userRepository.existsByEmail(dto.getNewEmail())){
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

    private void validatePasswordConfirmed(UserRegistrationDto dto){
        if(!dto.getConfirmPassword().equals(dto.getPassword())){
            throw new PasswordMismatchException();
        }
    }

    private void validateUserNotDeleted(User user) {
        if (!userRepository.existsByIdAndDeletedFalse(user.getId())) {
            throw new ForbiddenException();
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