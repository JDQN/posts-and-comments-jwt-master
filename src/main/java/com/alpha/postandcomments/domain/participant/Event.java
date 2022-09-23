package com.alpha.postandcomments.domain.participant;

import co.com.sofka.domain.generic.Entity;
import com.alpha.postandcomments.domain.participant.values.DateOfEvent;
import com.alpha.postandcomments.domain.participant.values.Detail;
import com.alpha.postandcomments.domain.participant.values.Element;
import com.alpha.postandcomments.domain.participant.values.EventId;
import com.alpha.postandcomments.domain.participant.values.Type;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode

public class Event extends Entity<EventId> {

    private DateOfEvent dateOfEvent;
    private Element element;
    private Type type;
    private Detail detail;

    public Event(EventId entityId, DateOfEvent dateOfEvent, Element element, Type type, Detail detail) {
        super(entityId);
        this.dateOfEvent = dateOfEvent;
        this.element = element;
        this.type = type;
        this.detail = detail;
    }

    public DateOfEvent dateOfEvent() {
        return dateOfEvent;
    }

    public Element element() {
        return element;
    }

    public Type type() {
        return type;
    }

    public Detail detail() {
        return detail;
    }
}
