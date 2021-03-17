package com.movienight.app.exception.model.user;

import com.movienight.app.exception.global.BadRequestException;

public class PasswordMismatchException extends BadRequestException {
    public PasswordMismatchException() {
        super("Password doesn't confirm");
    }
}
