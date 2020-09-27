package com.kharkiv.movienight.service.validation.model;

import com.kharkiv.movienight.exception.standard.ForbiddenException;
import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.service.actor.ActorUtils;
import com.kharkiv.movienight.service.validation.validator.AccessValidator;

public class UserAccessValidatorImpl implements AccessValidator {

    @Override
    public void validateChangeRole(User actor) {
        if(ActorUtils.isManager(actor) || ActorUtils.isUser(actor)){
            throw new ForbiddenException();
        }
    }

    @Override
    public void validateAgreement(User actor) {
        //permitAll
    }

    @Override
    public void validateFindAll(User actor) {
        if(ActorUtils.isUser(actor)){
            throw new ForbiddenException();
        }
    }

    @Override
    public void validateRestore(User actor) {
        // permitAll
    }

    @Override
    public void validateDelete(User actor) {
        // permitAll
    }

    @Override
    public void validateUpdate(User actor) {
        //permitAll
    }

    @Override
    public void validateRegistration(User actor) {
        //permitAll
    }
}
