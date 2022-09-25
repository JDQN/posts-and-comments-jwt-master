package com.alpha.postandcomments.domain.participant.events.commands;


import co.com.sofka.domain.generic.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReceiveMessage extends Command {

    private String messageId;
    private String participantId;
    private String name;
    private String content;
}
