package com.movienight.foundation.persistence.model.movie;

import com.movienight.foundation.persistence.model.event.Event;
import com.movienight.foundation.persistence.model.general.IdCreatedUpdatedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie extends IdCreatedUpdatedEntity {

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

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            mappedBy = "movie"
    )
    private List<Event> events = new ArrayList<>();
}
