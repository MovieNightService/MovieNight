package com.movienight.app.exception.model.userevent;

import com.movienight.app.exception.global.NotFoundException;

public class UserEventNotFoundException extends NotFoundException {
    public UserEventNotFoundException() {
        super("UserEvent not found");
    }
}
