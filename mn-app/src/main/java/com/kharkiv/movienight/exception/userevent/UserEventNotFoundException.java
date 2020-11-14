package com.kharkiv.movienight.exception.userevent;

import com.kharkiv.movienight.exception.standard.NotFoundException;

public class UserEventNotFoundException extends NotFoundException {
    public UserEventNotFoundException() {
        super("UserEvent not found");
    }
}
