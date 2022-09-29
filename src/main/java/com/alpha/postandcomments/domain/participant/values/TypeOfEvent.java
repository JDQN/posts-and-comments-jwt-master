package com.alpha.postandcomments.domain.participant.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class TypeOfEvent implements ValueObject<String> {

    private final String value;

    //creado, eliminado (canal y comentario); reacción (canal); enviado, recibido (mensaje); login (usuario).

    public TypeOfEvent(String value) {
        this.value = Objects.requireNonNull(value);
        if (!value.equals("Creado") && !value.equals("Eliminado")&& !value.equals("Reacción")
                && !value.equals("Enviado")
                && !value.equals("Recibido")&& !value.equals("LogIn")) {
            throw new IllegalArgumentException("Type of the event is invalid");
        }
    }

    @Override
    public String value() {
        return value;
    }

}
