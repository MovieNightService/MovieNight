package com.movienight.app.exception.model.user;

import com.movienight.app.exception.global.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("User not found");
    }
}