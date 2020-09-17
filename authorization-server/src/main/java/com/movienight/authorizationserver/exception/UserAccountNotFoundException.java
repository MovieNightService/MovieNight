package com.movienight.authorizationserver.exception;

public class UserAccountNotFoundException extends RuntimeException {

    public UserAccountNotFoundException() {
        super("Account not found");
    }
}

