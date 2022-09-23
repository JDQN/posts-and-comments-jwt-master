package com.alpha.postandcomments.domain.post.values;

import co.com.sofka.domain.generic.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

@ToString
@EqualsAndHashCode
public class Reaction implements ValueObject<String> {

    private final String value;

    public Reaction(String value){
        this.value = Objects.requireNonNull(value);
        if (!value.equals("Me Gusta") && !value.equals("Me Divierte") && !value.equals("Me Enoja")) {
            throw new IllegalArgumentException("Reaction has an invalid value");
        }
    }

    @Override
    public String value() {
        return value;
    }
}
