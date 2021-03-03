package com.movienight.app.exception.movie;

import com.movienight.app.exception.common.BadRequestException;

public class MovieRatingException extends BadRequestException {
    public MovieRatingException() {
        super("Raiting of movie can't be less than 0 or more than 10");
    }
}
