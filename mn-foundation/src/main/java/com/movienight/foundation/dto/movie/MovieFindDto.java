package com.movienight.foundation.dto.movie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieFindDto {

    private String name;
    private Integer duration;
    private Integer issue;
    private Double rating;
    private String language;
    private String description;
    private Integer age;
    private String genre;
}
