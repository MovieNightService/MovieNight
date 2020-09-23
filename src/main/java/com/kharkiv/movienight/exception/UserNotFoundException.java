package com.kharkiv.movienight.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Account not found");
    }
}


