package com.alpha.postandcomments.domain.post;

import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import com.alpha.postandcomments.domain.commons.values.Content;
import com.alpha.postandcomments.domain.commons.values.Name;
import com.alpha.postandcomments.domain.commons.values.ParticipantId;
import com.alpha.postandcomments.domain.commons.values.PhotoUrl;
import com.alpha.postandcomments.domain.commons.values.PostId;
import com.alpha.postandcomments.domain.post.events.CommentAdded;
import com.alpha.postandcomments.domain.post.events.CommentDeleted;
import com.alpha.postandcomments.domain.post.events.PostCreated;
import com.alpha.postandcomments.domain.post.events.PostDeleted;
import com.alpha.postandcomments.domain.post.events.ReactionAdded;
import com.alpha.postandcomments.domain.post.events.RelevanceVoteAdded;
import com.alpha.postandcomments.domain.post.values.CommentId;
import com.alpha.postandcomments.domain.post.values.Reaction;
import com.alpha.postandcomments.domain.post.values.RelevanceVote;
import com.alpha.postandcomments.domain.post.values.Title;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Post extends AggregateEvent<PostId> {

    protected Title title;

    protected Name name;

    protected PhotoUrl photoUrl;

    protected List<Reaction> reactions;

    protected RelevanceVote relevanceVote;

    protected ParticipantId participantId;

    protected Boolean deleted;

    protected List<Comment> comments;

    public Post(PostId entityId, Title title, Name name, PhotoUrl photoUrl, ParticipantId participantId) {
        super(entityId);
        subscribe(new PostChange(this));
        appendChange(new PostCreated(title.value(), name.value(), photoUrl.value(), participantId.value()));
    }


    private Post(PostId id) {
        super(id);
        subscribe(new PostChange(this));
    }

    public static Post from(PostId id, List<DomainEvent> events) {
        Post post = new Post(id);
        events.forEach(event -> post.applyEvent(event));
        return post;
    }

    //----------- BEHAVIORS

    public void addAComment(CommentId id, Name name, Content content, ParticipantId participantId) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(content);
        Objects.requireNonNull(participantId);
        appendChange(new CommentAdded(id.value(), name.value(), content.value(), participantId.value())).apply();
    }

    public void addReaction(Reaction reaction) {
        Objects.requireNonNull(reaction);
        appendChange(new ReactionAdded(reaction.value())).apply();
    }

    public void addRelevanceVote() {
        Objects.requireNonNull(relevanceVote);
        appendChange(new RelevanceVoteAdded("1")).apply();
    }

    public void deleteComment(CommentId commentId) {
        Objects.requireNonNull(commentId);
        appendChange(new CommentDeleted(commentId.value())).apply();
    }

    public void deletePost(PostId postId) {
        Objects.requireNonNull(postId);
        appendChange(new PostDeleted(postId.value())).apply();
    }

    //------------- FINDER

    public Optional<Comment> getCommentById(CommentId commentId) {
        return comments.stream().filter((comment -> comment.identity().equals(commentId))).findFirst();
    }


}
