package com.alpha.postandcomments.domain;

import co.com.sofka.domain.generic.Entity;
import com.alpha.postandcomments.domain.values.Content;
import com.alpha.postandcomments.domain.values.Author;
import com.alpha.postandcomments.domain.values.CommentId;

public class Comment extends Entity<CommentId> {

    private Author author;
    private Content content;


    public Comment(CommentId entityId, Author author, Content content) {
        super(entityId);
        this.author = author;
        this.content = content;
    }

    public Author author() {
        return author;
    }

    public Content content() {
        return content;
    }
}
