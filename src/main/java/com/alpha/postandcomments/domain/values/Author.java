package com.alpha.postandcomments.domain.values;

import co.com.sofka.domain.generic.ValueObject;

public class Author implements ValueObject<String> {

    private final String author;

    public Author(String author) {
        this.author = author;
    }

    @Override
    public String value() {
        return author;
    }
}
