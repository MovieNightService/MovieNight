package com.kharkiv.movienight.exception.user;

import com.kharkiv.movienight.exception.standard.NotFoundException;

public class UserDeletedException extends NotFoundException {
    public UserDeletedException() {
        super("User is deleted");
    }
}
