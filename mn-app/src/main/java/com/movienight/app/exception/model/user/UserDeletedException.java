package com.movienight.app.exception.model.user;

import com.movienight.app.exception.global.NotFoundException;

public class UserDeletedException extends NotFoundException {
    public UserDeletedException() {
        super("User is deleted");
    }
}
