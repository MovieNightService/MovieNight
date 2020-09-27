package com.kharkiv.movienight.exception.user;

import com.kharkiv.movienight.exception.standard.BadRequestException;

public class UserWithSuchEmailAlreadyExistsException extends BadRequestException {

    public UserWithSuchEmailAlreadyExistsException() {
        super("User with such email already exists");
    }
}
