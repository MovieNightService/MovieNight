package com.movienight.app.exception.user;

import com.movienight.app.exception.common.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("User not found");
    }
}