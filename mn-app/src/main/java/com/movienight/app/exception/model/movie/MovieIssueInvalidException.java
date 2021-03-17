package com.movienight.app.exception.model.movie;

import com.movienight.app.exception.global.BadRequestException;

public class MovieIssueInvalidException extends BadRequestException {
    public MovieIssueInvalidException() {
        super("Movie date of issue invalid");
    }
}
