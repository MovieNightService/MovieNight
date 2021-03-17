package com.movienight.app.exception.model.user;

import com.movienight.app.exception.global.BadRequestException;

public class UserDateOfBirthInvalidException extends BadRequestException {
    public UserDateOfBirthInvalidException() {
        super("User date of birth invalid");
    }
}