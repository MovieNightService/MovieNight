package com.movienight.app.exception.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    public NotFoundException(String message) {
        super(message);
    }
}

