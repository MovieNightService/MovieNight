package com.movienight.app.exception.model.event;

import com.movienight.app.exception.global.NotFoundException;

public class EventNotFoundException extends NotFoundException {
    public EventNotFoundException() {
        super("Event not found");
    }
}
