package com.movienight.app.exception.movie;

import com.movienight.app.exception.common.NotFoundException;

public class MovieNotFoundException extends NotFoundException {
    public MovieNotFoundException() {
        super("Movie not found");
    }
}
