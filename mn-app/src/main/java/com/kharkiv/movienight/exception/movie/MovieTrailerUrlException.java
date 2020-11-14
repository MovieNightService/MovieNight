package com.kharkiv.movienight.exception.movie;

import com.kharkiv.movienight.exception.standard.BadRequestException;

public class MovieTrailerUrlException extends BadRequestException {
    public MovieTrailerUrlException() {
        super("URL incorrect");
    }
}
