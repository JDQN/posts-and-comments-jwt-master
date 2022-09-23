package com.alpha.postandcomments.domain.participant;

import co.com.sofka.domain.generic.Entity;
import com.alpha.postandcomments.domain.commons.values.Name;
import com.alpha.postandcomments.domain.commons.values.ParticipantId;
import com.alpha.postandcomments.domain.participant.values.MessageId;
import com.alpha.postandcomments.domain.commons.values.Content;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Message extends Entity<MessageId> {

    private Name name;
    private Content content;

    public Message(MessageId entityId, Name name, Content content) {
        super(entityId);
        this.name = name;
        this.content = content;
    }

    public Name name() {
        return name;
    }

    public Content content() {
        return content;
    }
}
