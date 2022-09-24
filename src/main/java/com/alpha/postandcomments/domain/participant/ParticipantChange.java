package com.alpha.postandcomments.domain.participant;

import co.com.sofka.domain.generic.EventChange;
import com.alpha.postandcomments.domain.commons.values.Content;
import com.alpha.postandcomments.domain.commons.values.Name;
import com.alpha.postandcomments.domain.commons.values.PhotoUrl;
import com.alpha.postandcomments.domain.commons.values.PostId;
import com.alpha.postandcomments.domain.participant.events.EventCasted;
import com.alpha.postandcomments.domain.participant.events.FavAdded;
import com.alpha.postandcomments.domain.participant.events.MessageReceived;
import com.alpha.postandcomments.domain.participant.events.ParticipantCreated;
import com.alpha.postandcomments.domain.participant.values.DateOfEvent;
import com.alpha.postandcomments.domain.participant.values.Detail;
import com.alpha.postandcomments.domain.participant.values.Element;
import com.alpha.postandcomments.domain.participant.values.EventId;
import com.alpha.postandcomments.domain.participant.values.MessageId;
import com.alpha.postandcomments.domain.participant.values.Rol;
import com.alpha.postandcomments.domain.participant.values.TypeOfEvent;

import java.util.ArrayList;

public class ParticipantChange extends EventChange {

    public ParticipantChange(Participant participant){
        apply((ParticipantCreated event)->{
            participant.name = new Name(event.getName());
            participant.photoUrl = new PhotoUrl(event.getPhotoUrl());
            participant.rol = new Rol(event.getRol());
            participant.favorites = new ArrayList<>();
            participant.events = new ArrayList<>();
            participant.messages = new ArrayList<>();
        });

        apply((EventCasted event)->{
            Event participantEvent = new Event(
                    EventId.of(event.getEventId()),
                    new DateOfEvent(event.getDate()),
                    new Element(event.getElement()),
                    new TypeOfEvent(event.getTypeOfEvent()),
                    new Detail(event.getDetail()));
            participant.events.add(participantEvent);
        });

        apply((FavAdded event)->{
            participant.favorites.add(PostId.of(event.getPostId()));
        });

        apply((MessageReceived event)->{
            Message message = new Message(
                    MessageId.of(event.getMessageId()),
                    new Name(event.getName()),
                    new Content(event.getContent()));
            participant.messages.add(message);
        });
    }
}
