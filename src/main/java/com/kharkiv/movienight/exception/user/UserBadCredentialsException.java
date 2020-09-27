package com.kharkiv.movienight.exception.user;

import com.kharkiv.movienight.exception.standard.UnauthorizedException;

public class UserBadCredentialsException extends UnauthorizedException {

    public UserBadCredentialsException() {
        super("Invalid password");
    }
}
