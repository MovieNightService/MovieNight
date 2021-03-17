package com.movienight.app.exception.model.user;

import com.movienight.app.exception.global.BadRequestException;

public class UserWithSuchPhoneAlreadyExistsException extends BadRequestException {
    public UserWithSuchPhoneAlreadyExistsException() {
        super("User with such phone already exists in system");
    }
}
