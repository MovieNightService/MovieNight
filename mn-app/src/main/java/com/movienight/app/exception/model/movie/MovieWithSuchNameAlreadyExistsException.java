package com.movienight.app.exception.model.movie;

import com.movienight.app.exception.global.BadRequestException;

public class MovieWithSuchNameAlreadyExistsException extends BadRequestException {
    public MovieWithSuchNameAlreadyExistsException() {
        super("Movie with such name already exists in system");
    }
}
