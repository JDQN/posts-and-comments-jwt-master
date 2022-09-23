package com.alpha.postandcomments.domain.post.events;

import co.com.sofka.domain.generic.DomainEvent;

public class CommentAdded extends DomainEvent {

    private String id;
    private String author;
    private String content;
    private String participantId;


    public CommentAdded(String id, String author, String content, String participantId) {
        super("com.alpha.postandcomments.domain.post.events.CommentAdded");
        this.id = id;
        this.author = author;
        this.content = content;
        this.participantId = participantId;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getParticipantId() {
        return participantId;
    }
}
