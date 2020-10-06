package com.kharkiv.movienight.persistence.model.movie;

import com.kharkiv.movienight.persistence.model.event.Event;
import com.kharkiv.movienight.persistence.model.general.IdCreatedUpdatedDeletedEntity;
import com.kharkiv.movienight.persistence.model.user.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie extends IdCreatedUpdatedDeletedEntity {

    private String name;

    private Integer duration;

    private Instant issue;

    private Double rating;

    private String language;

    private String description;

    private String trailer;

    private Integer age;

    @ElementCollection(targetClass = Genre.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "movie_genre", joinColumns = @JoinColumn(name = "movie_id"))
    private Set<Genre> genre = new HashSet<>();

//    @OneToMany(
//            fetch = FetchType.LAZY,
//            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
//            mappedBy = "movie"
//    )
//    private List<Event> events = new ArrayList<>();
}
