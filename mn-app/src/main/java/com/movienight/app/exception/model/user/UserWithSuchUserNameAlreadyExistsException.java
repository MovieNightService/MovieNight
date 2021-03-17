package com.movienight.app.exception.model.user;

import com.movienight.app.exception.global.BadRequestException;

public class UserWithSuchUserNameAlreadyExistsException extends BadRequestException {
    public UserWithSuchUserNameAlreadyExistsException() {
        super("User with such username already exists in system");
    }
}
