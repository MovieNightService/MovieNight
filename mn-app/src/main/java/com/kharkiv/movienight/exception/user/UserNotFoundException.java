package com.kharkiv.movienight.exception.user;

import com.kharkiv.movienight.exception.standard.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("User not found");
    }
}