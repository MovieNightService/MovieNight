package com.movienight.app.exception.model.movie;

import com.movienight.app.exception.global.BadRequestException;

public class MovieDurationException extends BadRequestException {
    public MovieDurationException() {
        super("Duration of movie can't be equals or less than 0");
    }
}
