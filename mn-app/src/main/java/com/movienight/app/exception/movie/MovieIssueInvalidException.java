package com.movienight.app.exception.movie;

import com.movienight.app.exception.common.BadRequestException;

public class MovieIssueInvalidException extends BadRequestException {
    public MovieIssueInvalidException() {
        super("Movie date of issue invalid");
    }
}
