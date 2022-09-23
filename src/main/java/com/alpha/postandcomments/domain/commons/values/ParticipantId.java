package com.alpha.postandcomments.domain.commons.values;

import co.com.sofka.domain.generic.Identity;
import com.alpha.postandcomments.domain.post.values.CommentId;

public class ParticipantId extends Identity {

    private ParticipantId(String uuid){
        super(uuid);
    }

    public ParticipantId(){

    }

    public static ParticipantId of(String uuid){
        return new ParticipantId(uuid);
    }
}
