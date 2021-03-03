package com.movienight.app.exception.event;

import com.movienight.app.exception.common.NotFoundException;

public class EventNotFoundException extends NotFoundException {
    public EventNotFoundException() {
        super("Event not found");
    }
}
