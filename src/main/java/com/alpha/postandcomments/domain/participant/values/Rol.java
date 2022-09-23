package com.alpha.postandcomments.domain.participant.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class Rol implements ValueObject<String> {

    private final String value;

    public Rol(String value) {
        this.value = Objects.requireNonNull(value);
        if (!value.equals("ADMIN") && !value.equals("USER") ) {
            throw new IllegalArgumentException("Participant rol is invalid");
        }
    }

    @Override
    public String value() {
        return value;
    }
}
