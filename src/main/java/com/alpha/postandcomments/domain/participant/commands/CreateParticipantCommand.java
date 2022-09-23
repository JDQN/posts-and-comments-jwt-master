package com.alpha.postandcomments.domain.participant.commands;

import co.com.sofka.domain.generic.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateParticipantCommand extends Command {

    private String participantId;
    private String name;
    private String photoUrl;
    private String rol;
}
