package com.kharkiv.movienight.exception.user;

import com.kharkiv.movienight.exception.standard.BadRequestException;

public class UserWithSuchPhoneAlreadyExistsException extends BadRequestException {
    public UserWithSuchPhoneAlreadyExistsException() {
        super("User with such phone already exists in system");
    }
}
