package com.kharkiv.movienight.persistence.model.event;

import com.kharkiv.movienight.persistence.model.general.IdCreatedUpdatedDeletedEntity;
import com.kharkiv.movienight.persistence.model.movie.Movie;
import com.kharkiv.movienight.persistence.model.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event extends IdCreatedUpdatedDeletedEntity {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    private Instant date;

    @OneToOne(mappedBy = "event")
    private UserEvent userEvent;
}
