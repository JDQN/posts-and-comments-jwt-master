package com.alpha.postandcomments.domain.commons.values;

import co.com.sofka.domain.generic.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

@ToString
@EqualsAndHashCode
public class PhotoUrl implements ValueObject<String> {

    private final String value;


    public PhotoUrl(String value) {
        this.value = Objects.requireNonNull(value);;
    }

    @Override
    public String value() {
        return value;
    }
}
