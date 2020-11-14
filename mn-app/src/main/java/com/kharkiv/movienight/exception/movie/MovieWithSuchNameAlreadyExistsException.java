package com.kharkiv.movienight.exception.movie;

import com.kharkiv.movienight.exception.standard.BadRequestException;

public class MovieWithSuchNameAlreadyExistsException extends BadRequestException {
    public MovieWithSuchNameAlreadyExistsException() {
        super("Movie with such name already exists in system");
    }
}
