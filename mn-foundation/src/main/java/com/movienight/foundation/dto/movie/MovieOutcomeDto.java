package com.movienight.foundation.dto.movie;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class MovieOutcomeDto {

    private Long id;
    private String name;
    private Integer duration;
    private Integer issue;
    private Double rating;
    private String language;
    private String description;
    private String trailer;
    private Integer age;
    private String genre;
    private String image;
    private Long createdBy;
    private Instant createdAt;
    private Long updatedBy;
    private Instant updatedAt;
}
