package com.kharkiv.movienight.exception.standard;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN.getReasonPhrase());
    }

    public ForbiddenException(String message) {
        super(message);
    }
}