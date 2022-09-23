package com.alpha.postandcomments.domain.post.events;

import co.com.sofka.domain.generic.DomainEvent;
import lombok.Getter;

@Getter

public class RelevanceVoteAdded extends DomainEvent {

    private String relevanceVote;

    public RelevanceVoteAdded(String relevanceVote){
        super("com.alpha.postandcomments.domain.post.events.RelevanceVoteAdded");
        this.relevanceVote=relevanceVote;
    }
}
