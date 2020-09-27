package com.kharkiv.movienight.exception.user;

import com.kharkiv.movienight.exception.standard.BadRequestException;

public class UserWithSuchEmailAlreadyExists extends BadRequestException {
    public UserWithSuchEmailAlreadyExists() {
        super("User with such email already exists in system");
    }
}
