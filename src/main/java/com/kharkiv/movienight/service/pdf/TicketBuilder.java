package com.kharkiv.movienight.service.pdf;

import com.kharkiv.movienight.service.actor.ActorService;

public interface TicketBuilder extends ActorService {

    void buildPDF(Long eventId);
}
