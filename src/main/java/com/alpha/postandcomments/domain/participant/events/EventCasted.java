package com.alpha.postandcomments.domain.participant.events;

import co.com.sofka.domain.generic.DomainEvent;

import java.util.UUID;

public class EventCasted extends DomainEvent {

    private String eventId;
    private String date;
    private String element;
    private String type;
    private String detail;

    public EventCasted(String eventId, String date, String element, String type, String detail) {
        super("com.alpha.postandcomments.domain.participant.events.EventCasted");
        this.eventId = eventId;
        this.date = date;
        this.element = element;
        this.type = type;
        this.detail = detail;
    }

    public String getEventId() {
        return eventId;
    }

    public String getDate() {
        return date;
    }

    public String getElement() {
        return element;
    }

    public String getType() {
        return type;
    }

    public String getDetail() {
        return detail;
    }
}
