package com.kharkiv.movienight.exception.user;

import com.kharkiv.movienight.exception.standard.BadRequestException;

public class UserWithSuchPhoneAlreadyExists extends BadRequestException {
    public UserWithSuchPhoneAlreadyExists() {
        super("User with such phone already exists in system");
    }
}