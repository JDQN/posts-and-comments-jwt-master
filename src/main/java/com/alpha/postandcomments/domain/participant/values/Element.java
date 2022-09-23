package com.alpha.postandcomments.domain.participant.values;


import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class Element implements ValueObject<String> {

    private final String value;

    public Element(String value) {
        this.value = Objects.requireNonNull(value);
        if (!value.equals("Canal") && !value.equals("Comentario")&& !value.equals("Mensaje") && !value.equals("Usuario")) {
            throw new IllegalArgumentException("Element of the event is invalid");
        }
    }

    @Override
    public String value() {
        return value;
    }
}
