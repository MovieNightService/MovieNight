package com.kharkiv.movienight.exception.user;

import com.kharkiv.movienight.exception.standard.BadRequestException;

public class UserDateOfBirthInvalidException extends BadRequestException {
    public UserDateOfBirthInvalidException() {
        super("User date of birth invalid");
    }
}