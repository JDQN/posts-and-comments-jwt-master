package com.alpha.postandcomments.domain;

import co.com.sofka.domain.generic.EventChange;
import com.alpha.postandcomments.domain.events.CommentAdded;
import com.alpha.postandcomments.domain.events.PostCreated;
import com.alpha.postandcomments.domain.values.Content;
import com.alpha.postandcomments.domain.values.Title;
import com.alpha.postandcomments.domain.values.Author;
import com.alpha.postandcomments.domain.values.CommentId;

import java.util.ArrayList;

public class PostChange extends EventChange {

    public PostChange(Post post){
        apply((PostCreated event)-> {
            post.title = new Title(event.getTitle());
            post.author = new Author(event.getAuthor());
            post.comments = new ArrayList<>();
        });

        apply((CommentAdded event)-> {
            Comment comment = new Comment(CommentId.of(event.getId()), new Author(event.getAuthor()), new Content(event.getContent()));
            post.comments.add(comment);
        });
    }
}
