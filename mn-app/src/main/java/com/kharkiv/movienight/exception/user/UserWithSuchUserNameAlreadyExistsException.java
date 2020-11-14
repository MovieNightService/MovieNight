package com.kharkiv.movienight.exception.user;

import com.kharkiv.movienight.exception.standard.BadRequestException;

public class UserWithSuchUserNameAlreadyExistsException extends BadRequestException {
    public UserWithSuchUserNameAlreadyExistsException() {
        super("User with such username already exists in system");
    }
}
