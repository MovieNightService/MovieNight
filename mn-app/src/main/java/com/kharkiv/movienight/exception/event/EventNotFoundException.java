package com.kharkiv.movienight.exception.event;

import com.kharkiv.movienight.exception.standard.NotFoundException;

public class EventNotFoundException extends NotFoundException {
    public EventNotFoundException() {
        super("Event not found");
    }
}
