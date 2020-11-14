package com.kharkiv.movienight.exception.movie;

import com.kharkiv.movienight.exception.standard.NotFoundException;

public class MovieNotFoundException extends NotFoundException {
    public MovieNotFoundException() {
        super("Movie not found");
    }
}
