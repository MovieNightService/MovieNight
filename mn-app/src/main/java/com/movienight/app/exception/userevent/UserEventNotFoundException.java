package com.movienight.app.exception.userevent;

import com.movienight.app.exception.common.NotFoundException;

public class UserEventNotFoundException extends NotFoundException {
    public UserEventNotFoundException() {
        super("UserEvent not found");
    }
}
