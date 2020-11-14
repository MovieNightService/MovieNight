package com.kharkiv.movienight.exception.user;

import com.kharkiv.movienight.exception.standard.BadRequestException;

public class PasswordMismatchException extends BadRequestException {
    public PasswordMismatchException() {
        super("Password doesn't confirm");
    }
}
