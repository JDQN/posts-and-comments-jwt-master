package com.alpha.postandcomments.domain.participant.events.commands;

import co.com.sofka.domain.generic.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CastEvent extends Command {

    private String eventId;
    private String participantId;
    private String date;
    private String element;
    private String typeOfEvent;
    private String detail;
}
