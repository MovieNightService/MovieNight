package com.kharkiv.movienight.exception.user;

import com.kharkiv.movienight.exception.standard.BadRequestException;

public class UserWithSuchUserNameAlreadyExists extends BadRequestException {
    public UserWithSuchUserNameAlreadyExists() {
        super("User with such username already exists in system");
    }
}