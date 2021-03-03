package com.movienight.app.exception.movie;

import com.movienight.app.exception.common.BadRequestException;

public class MovieWithSuchNameAlreadyExistsException extends BadRequestException {
    public MovieWithSuchNameAlreadyExistsException() {
        super("Movie with such name already exists in system");
    }
}
