package com.movienight.app.exception.user;

import com.movienight.app.exception.common.BadRequestException;

public class PasswordMismatchException extends BadRequestException {
    public PasswordMismatchException() {
        super("Password doesn't confirm");
    }
}
