package com.movienight.app.exception.user;

import com.movienight.app.exception.common.BadRequestException;

public class UserDateOfBirthInvalidException extends BadRequestException {
    public UserDateOfBirthInvalidException() {
        super("User date of birth invalid");
    }
}