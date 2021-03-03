package com.movienight.app.exception.user;

import com.movienight.app.exception.common.BadRequestException;

public class UserWithSuchUserNameAlreadyExistsException extends BadRequestException {
    public UserWithSuchUserNameAlreadyExistsException() {
        super("User with such username already exists in system");
    }
}
