package com.alpha.postandcomments.domain.values;

import co.com.sofka.domain.generic.ValueObject;

public class Content implements ValueObject<String> {

    private final String content;

    public Content(String content) {
        this.content = content;
    }

    @Override
    public String value() {
        return content;
    }
}
