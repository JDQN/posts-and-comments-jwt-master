package com.alpha.postandcomments.domain.post.events;

import co.com.sofka.domain.generic.DomainEvent;
import lombok.Getter;

@Getter
public class ReactionAdded extends DomainEvent {

    private String reaction;


    public ReactionAdded(String reaction) {
        super("com.alpha.postandcomments.domain.post.events.ReactionAdded");
        this.reaction = reaction;
    }
}
