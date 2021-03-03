package com.movienight.app.exception.user;

import com.movienight.app.exception.common.UnauthorizedException;

public class UserBadCredentialsException extends UnauthorizedException {
    public UserBadCredentialsException(String message) {
        super(message);
    }
}
