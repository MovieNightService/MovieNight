package com.kharkiv.movienight.exception.movie;

import com.kharkiv.movienight.exception.standard.BadRequestException;

public class MovieIssueInvalidException extends BadRequestException {
    public MovieIssueInvalidException() {
        super("Movie date of issue invalid");
    }
}
