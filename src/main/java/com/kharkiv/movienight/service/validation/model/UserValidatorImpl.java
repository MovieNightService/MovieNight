package com.kharkiv.movienight.service.validation.model;

import com.kharkiv.movienight.exception.standard.BadRequestException;
import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.service.validation.type.MethodType;
import com.kharkiv.movienight.service.validation.validator.Validator;

public class UserValidatorImpl implements Validator {

    @Override
    public void validate(MethodType methodType, Object... objects) {
        User actor = getActorFromContext();
        Object object = null;
        User user = null;

        if (objects.length > 0) {
            object = objects[0];
        }

        if (object instanceof User) {
            user = (User) object;
        }

        executeValidation(methodType, actor, user);
    }

    public void executeValidation(MethodType methodType, User actor, User user){
        switch (methodType) {
            case REGISTRATION:
                validateRegistration(actor, user);
                break;
            case UPDATE:
                validateUpdate(actor, user);
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
            case AGREEMENT:
                validateAgreement(actor, user);
                break;
            case CHANGE_ROLE:
                validateChangeRole(actor, user);
                break;
            case PERMISSION_READ:
                validatePermissionRead(actor);
                break;
            case PERMISSION_WRITE:
                validatePermissionWrite(actor);
                break;
            default:
                throw new BadRequestException("Incorrect METHOD_TYPE");
        }
    }

    public void validateChangeRole(User actor, User user) {

    }

    public void validateAgreement(User actor, User user) {

    }

    public void validateFindAll(User actor) {

    }

    public void validateRestore(User actor, User user) {

    }

    public void validateDelete(User actor, User user) {

    }

    public void validateUpdate(User actor, User user) {

    }

    public void validateRegistration(User actor, User user) {

    }

    public void validatePermissionRead(User actor) {

    }

    public void validatePermissionWrite(User actor) {

    }
}
