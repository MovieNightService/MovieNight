package com.movienight.app.exception.movie;

import com.movienight.app.exception.common.BadRequestException;

public class MovieTrailerUrlException extends BadRequestException {
    public MovieTrailerUrlException() {
        super("URL incorrect");
    }
}
