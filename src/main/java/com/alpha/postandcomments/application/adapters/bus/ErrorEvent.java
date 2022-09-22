package com.alpha.postandcomments.application.adapters.bus;

import co.com.sofka.domain.generic.DomainEvent;
import lombok.Getter;

@Getter
public class ErrorEvent extends DomainEvent {
    private final String classType;
    private final String message;

    public ErrorEvent(String classType, String message){
        super("post.error");
        this.classType = classType;
        this.message = message;
    }
}
