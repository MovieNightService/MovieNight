package com.kharkiv.movienight.service.validation.validator;

import com.kharkiv.movienight.exception.standard.BadRequestException;
import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.service.actor.ActorService;
import com.kharkiv.movienight.service.validation.type.MethodType;

public interface AccessValidator extends ActorService {

    default void validate(MethodType methodType) {
        User actor = getActorFromContext();

        switch (methodType){
            case REGISTRATION:
                validateRegistration(actor);
                break;
            case UPDATE:
                validateUpdate(actor);
                break;
            case DELETE:
                validateDelete(actor);
                break;
            case RESTORE:
                validateRestore(actor);
                break;
            case FIND_ALL:
                validateFindAll(actor);
                break;
            case AGREEMENT:
                validateAgreement(actor);
                break;
            case CHANGE_ROLE:
                validateChangeRole(actor);
                break;
            default:
                throw new BadRequestException("Incorrect METHOD_TYPE");
        }
    }

    void validateChangeRole(User actor);

    void validateAgreement(User actor);

    void validateFindAll(User actor);

    void validateRestore(User actor);

    void validateDelete(User actor);

    void validateUpdate(User actor);

    void validateRegistration(User actor);
}
