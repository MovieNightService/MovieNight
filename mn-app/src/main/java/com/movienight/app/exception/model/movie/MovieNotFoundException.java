package com.movienight.app.exception.model.movie;

import com.movienight.app.exception.global.NotFoundException;

public class MovieNotFoundException extends NotFoundException {
    public MovieNotFoundException() {
        super("Movie not found");
    }
}
