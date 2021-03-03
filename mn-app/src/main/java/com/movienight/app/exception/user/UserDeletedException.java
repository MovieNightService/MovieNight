package com.movienight.app.exception.user;

import com.movienight.app.exception.common.NotFoundException;

public class UserDeletedException extends NotFoundException {
    public UserDeletedException() {
        super("User is deleted");
    }
}
