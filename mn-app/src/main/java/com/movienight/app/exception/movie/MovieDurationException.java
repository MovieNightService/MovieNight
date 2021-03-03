package com.movienight.app.exception.movie;

import com.movienight.app.exception.common.BadRequestException;

public class MovieDurationException extends BadRequestException {
    public MovieDurationException() {
        super("Duration of movie can't be equals or less than 0");
    }
}
