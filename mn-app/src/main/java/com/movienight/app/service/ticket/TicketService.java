package com.movienight.app.service.ticket;

import com.movienight.app.service.utils.actor.ActorService;
import com.movienight.foundation.persistence.model.userevent.UserEvent;

public interface TicketService extends ActorService {

    void create(UserEvent userEvent);
}
