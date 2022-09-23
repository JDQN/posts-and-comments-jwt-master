package com.alpha.postandcomments.domain.participant.events;

import co.com.sofka.domain.generic.DomainEvent;
import lombok.Getter;

@Getter
public class ParticipantCreated extends DomainEvent {

    private String name;
    private String photoUrl;
    private String rol;

    public ParticipantCreated(String type) {
        super("com.alpha.postandcomments.domain.participant.events.ParticipantCreated");
    }

    public ParticipantCreated(String name, String photoUrl, String rol) {
        super("com.alpha.postandcomments.domain.participant.events.ParticipantCreated");
        this.name = name;
        this.photoUrl = photoUrl;
        this.rol = rol;
    }
}
