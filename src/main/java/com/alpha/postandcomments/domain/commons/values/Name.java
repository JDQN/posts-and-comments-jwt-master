package com.alpha.postandcomments.domain.commons.values;

import co.com.sofka.domain.generic.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Name implements ValueObject<String> {

    private final String author;

    public Name(String author) {
        this.author = author;
    }

    @Override
    public String value() {
        return author;
    }
}
