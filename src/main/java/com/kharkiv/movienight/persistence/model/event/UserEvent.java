package com.kharkiv.movienight.persistence.model.event;

import com.kharkiv.movienight.persistence.model.general.IdCreatedUpdatedDeletedEntity;
import com.kharkiv.movienight.persistence.model.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users_event")
public class UserEvent extends IdCreatedUpdatedDeletedEntity {

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;
}
