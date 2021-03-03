package com.movienight.foundation.persistence.model.userevent;

import com.movienight.foundation.persistence.model.event.Event;
import com.movienight.foundation.persistence.model.general.IdEntity;
import com.movienight.foundation.persistence.model.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_event")
public class UserEvent extends IdEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;
}
