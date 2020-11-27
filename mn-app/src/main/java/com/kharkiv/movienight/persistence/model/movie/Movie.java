package com.kharkiv.movienight.persistence.model.movie;

import com.kharkiv.movienight.persistence.model.event.Event;
import com.kharkiv.movienight.persistence.model.general.IdCreatedUpdatedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private byte[] image;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            mappedBy = "movie"
    )
    private List<Event> events = new ArrayList<>();
}
