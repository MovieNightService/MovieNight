package com.movienight.app.exception.model.movie;

import com.movienight.app.exception.global.BadRequestException;

public class MovieTrailerUrlException extends BadRequestException {
    public MovieTrailerUrlException() {
        super("URL incorrect");
    }
}
