package com.alpha.postandcomments.domain.post.events;

import co.com.sofka.domain.generic.DomainEvent;
import lombok.Getter;


@Getter
public class PostCreated extends DomainEvent {
    private String title;
    private String name;
    private String photoUrl;
    private String participantId;


    public PostCreated() {
        super("com.alpha.postandcomments.domain.post.events.PostCreated");
    }

    public PostCreated(String title, String name, String photoUrl, String participantId) {
        super("com.alpha.postandcomments.domain.post.events.PostCreated");
        this.title = title;
        this.name = name;
        this.photoUrl = photoUrl;
        this.participantId = participantId;
    }


}
