package com.alpha.postandcomments.domain.post;

import co.com.sofka.domain.generic.EventChange;
import com.alpha.postandcomments.domain.commons.values.ParticipantId;
import com.alpha.postandcomments.domain.post.events.CommentAdded;
import com.alpha.postandcomments.domain.post.events.CommentDeleted;
import com.alpha.postandcomments.domain.post.events.PostCreated;
import com.alpha.postandcomments.domain.post.events.PostDeleted;
import com.alpha.postandcomments.domain.post.events.ReactionAdded;
import com.alpha.postandcomments.domain.post.events.RelevanceVoteAdded;
import com.alpha.postandcomments.domain.post.values.CommentId;
import com.alpha.postandcomments.domain.commons.values.Content;
import com.alpha.postandcomments.domain.commons.values.Name;
import com.alpha.postandcomments.domain.commons.values.PhotoUrl;
import com.alpha.postandcomments.domain.post.values.Reaction;
import com.alpha.postandcomments.domain.post.values.RelevanceVote;
import com.alpha.postandcomments.domain.post.values.Title;


import java.util.ArrayList;


public class PostChange extends EventChange {

    public PostChange(Post post) {
        apply((PostCreated event) -> {
            post.title = new Title(event.getTitle());
            post.name = new Name(event.getName());
            post.photoUrl = new PhotoUrl(event.getPhotoUrl());
            post.participantId = ParticipantId.of(event.getParticipantId());
            post.reactions = new ArrayList<>();
            post.relevanceVote = new RelevanceVote("0");
            post.comments = new ArrayList<>();
            post.deleted = false;

        });

        apply((CommentAdded event) -> {
            Comment comment = new Comment(CommentId.of(event.getId()),
                    new Name(event.getAuthor()),
                    new Content(event.getContent()),
                    ParticipantId.of(event.getParticipantId()));
            post.comments.add(comment);
        });

        apply((CommentDeleted event) -> {
            post.comments.removeIf(comment -> comment.identity().equals(event.getCommentId()));
        });

        apply((PostDeleted event) -> {
            post.deleted = true;
        });

        apply((ReactionAdded event) -> {
            var reaction = new Reaction(event.getReaction());
            post.reactions.add(reaction);
        });

        apply((RelevanceVoteAdded event) -> {
            var relevanceVote = new RelevanceVote(event.getRelevanceVote());
            var newRelevance = Integer.parseInt(relevanceVote.value()) + Integer.parseInt(post.relevanceVote.value());
            post.relevanceVote = new RelevanceVote(String.valueOf(newRelevance));
        });
    }
}
