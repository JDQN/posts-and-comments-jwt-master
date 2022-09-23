package com.alpha.postandcomments.domain.post.values;

import co.com.sofka.domain.generic.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.validator.GenericValidator;

import java.util.Objects;

@ToString
@EqualsAndHashCode
public class RelevanceVote implements ValueObject<String> {

    private final String value;

    public RelevanceVote(String value){
        this.value = Objects.requireNonNull(value);
        if (!GenericValidator.isInt(value)) {
            throw new IllegalArgumentException("Relevance vote must be an Integer");
        }
    }

    @Override
    public String value() {
        return value;
    }
}
