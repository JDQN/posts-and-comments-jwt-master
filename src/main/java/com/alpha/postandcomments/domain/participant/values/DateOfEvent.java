package com.alpha.postandcomments.domain.participant.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class DateOfEvent implements ValueObject<String> {

    private final String value;

    public DateOfEvent(String value){
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public String value() {
        return value;
    }

}
