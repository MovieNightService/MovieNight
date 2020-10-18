package com.kharkiv.movienight.service.ticket;

import com.kharkiv.movienight.persistence.model.userevent.UserEvent;
import com.kharkiv.movienight.service.actor.ActorService;

public interface TicketService extends ActorService {

    void create(UserEvent userEvent);
}
