package com.kharkiv.movienight.transport.dto.movie;

import com.kharkiv.movienight.persistence.model.movie.Genre;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class MovieFindDto {

    private String name;
    private Integer duration;
    private Instant issue;
    private Double rating;
    private String language;
    private String description;
    private Integer age;
    private List<Genre> genre;
}
