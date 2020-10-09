package com.kharkiv.movienight.exception.movie;

import com.kharkiv.movienight.exception.standard.BadRequestException;

public class MovieRatingException extends BadRequestException {
    public MovieRatingException() {
        super("Raiting of movie can't be less than 0 or more than 10");
    }
}
