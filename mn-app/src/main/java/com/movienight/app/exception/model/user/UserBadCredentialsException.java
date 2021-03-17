package com.movienight.app.exception.model.user;

import com.movienight.app.exception.global.UnauthorizedException;

public class UserBadCredentialsException extends UnauthorizedException {
    public UserBadCredentialsException(String message) {
        super(message);
    }
}
