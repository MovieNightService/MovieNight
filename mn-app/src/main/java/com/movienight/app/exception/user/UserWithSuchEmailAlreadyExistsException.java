package com.movienight.app.exception.user;

import com.movienight.app.exception.common.BadRequestException;

public class UserWithSuchEmailAlreadyExistsException extends BadRequestException {

    public UserWithSuchEmailAlreadyExistsException() {
        super("User with such email already exists");
    }

    public UserWithSuchEmailAlreadyExistsException(String message) {
        super(message);
    }
}
