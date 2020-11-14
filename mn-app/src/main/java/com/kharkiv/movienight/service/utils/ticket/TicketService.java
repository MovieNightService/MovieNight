package com.kharkiv.movienight.service.utils.ticket;

import com.kharkiv.movienight.persistence.model.userevent.UserEvent;
import com.kharkiv.movienight.service.utils.actor.ActorService;

public interface TicketService extends ActorService {

    void create(UserEvent userEvent);
}
