package com.alpha.postandcomments.domain.participant.values;

import co.com.sofka.domain.generic.Identity;

public class EventId extends Identity {

    private EventId(String uuid) {
        super(uuid);
    }

    public EventId() {

    }

    public static EventId of(String uuid) {
        return new EventId(uuid);
    }
}
