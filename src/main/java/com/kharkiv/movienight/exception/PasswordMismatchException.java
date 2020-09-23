package com.kharkiv.movienight.exception;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException() {
        super("Password doesn't confirm");
    }
}
