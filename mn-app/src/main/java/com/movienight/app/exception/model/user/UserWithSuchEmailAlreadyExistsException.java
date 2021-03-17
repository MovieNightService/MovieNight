package com.movienight.app.exception.model.user;

import com.movienight.app.exception.global.BadRequestException;

public class UserWithSuchEmailAlreadyExistsException extends BadRequestException {

    public UserWithSuchEmailAlreadyExistsException() {
        super("User with such email already exists");
    }

    public UserWithSuchEmailAlreadyExistsException(String message) {
        super(message);
    }
}
