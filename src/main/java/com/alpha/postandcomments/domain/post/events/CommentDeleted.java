package com.alpha.postandcomments.domain.post.events;

import co.com.sofka.domain.generic.DomainEvent;
import lombok.Getter;

@Getter
public class CommentDeleted extends DomainEvent {

    private String commentId;

    public CommentDeleted(String commentId) {
        super("com.alpha.postandcomments.domain.post.events.CommentDeleted");
        this.commentId = commentId;
    }
}
