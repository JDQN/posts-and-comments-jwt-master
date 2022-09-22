package com.alpha.postandcomments.application.generic.models;

import co.com.sofka.domain.generic.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class StoredEvent {

    private String eventBody;
    private Date occurredOn;
    private String typeName;

}
