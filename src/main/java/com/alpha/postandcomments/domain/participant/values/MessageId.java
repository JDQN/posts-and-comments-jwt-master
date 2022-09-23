package com.alpha.postandcomments.domain.participant.values;

import co.com.sofka.domain.generic.Identity;

public class MessageId extends Identity {

    private MessageId(String uuid) {
        super(uuid);
    }

    public MessageId() {

    }

    public static MessageId of(String uuid) {
        return new MessageId(uuid);
    }
}
