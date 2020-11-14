package com.kharkiv.movienight.persistence.model.movie;

import com.kharkiv.movienight.persistence.model.general.IdCreatedUpdatedDeletedEntity;
import com.kharkiv.movienight.persistence.model.general.IdCreatedUpdatedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie extends IdCreatedUpdatedEntity {

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
