package com.kharkiv.movienight.exception.movie;

import com.kharkiv.movienight.exception.standard.BadRequestException;

public class MovieDurationException extends BadRequestException {
    public MovieDurationException() {
        super("Duration of movie can't be equals or less than 0");
    }
}
