package com.movienight.app.exception.user;

import com.movienight.app.exception.common.BadRequestException;

public class UserWithSuchPhoneAlreadyExistsException extends BadRequestException {
    public UserWithSuchPhoneAlreadyExistsException() {
        super("User with such phone already exists in system");
    }
}
