package com.alpha.postandcomments.domain.participant.events;

import co.com.sofka.domain.generic.DomainEvent;

public class FavAdded extends DomainEvent {

    private String postId;

    public FavAdded(String postId){
        super("com.alpha.postandcomments.domain.participant.events.FavAdded");
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }
}
