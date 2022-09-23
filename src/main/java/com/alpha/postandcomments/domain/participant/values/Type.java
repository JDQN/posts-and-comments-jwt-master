package com.alpha.postandcomments.domain.participant.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class Type implements ValueObject<String> {

    private final String value;

    //creado, eliminado (canal y comentario); reacción (canal); enviado, recibido (mensaje); login (usuario).

    public Type(String value) {
        this.value = Objects.requireNonNull(value);
        if (!value.equals("Creado") && !value.equals("Eliminado")&& !value.equals("Reacción")
                && !value.equals("recibido")&& !value.equals("LogIn")) {
            throw new IllegalArgumentException("Type of the event is invalid");
        }
    }

    @Override
    public String value() {
        return value;
    }

}
