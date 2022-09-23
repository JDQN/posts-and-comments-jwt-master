package com.alpha.postandcomments.domain.participant.events;

import co.com.sofka.domain.generic.DomainEvent;
import lombok.Getter;

import java.util.UUID;

@Getter
public class MessageReceived extends DomainEvent {

    private String messageId;
    private String name;
    private String content;

    public MessageReceived(String messageId, String name, String content) {
        super("com.alpha.postandcomments.domain.participant.events.MessageReceived");
        this.messageId = messageId;
        this.name = name;
        this.content = content;
    }
}
