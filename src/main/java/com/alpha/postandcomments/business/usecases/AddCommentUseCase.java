package com.alpha.postandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.alpha.postandcomments.domain.commons.values.ParticipantId;
import com.alpha.postandcomments.domain.post.Post;
import com.alpha.postandcomments.domain.commons.values.Content;
import com.alpha.postandcomments.business.gateways.DomainEventRepository;
import com.alpha.postandcomments.business.gateways.EventBus;
import com.alpha.postandcomments.business.generic.UseCaseForCommand;
import com.alpha.postandcomments.domain.post.commands.AddCommentCommand;
import com.alpha.postandcomments.domain.commons.values.Name;
import com.alpha.postandcomments.domain.post.values.CommentId;
import com.alpha.postandcomments.domain.commons.values.PostId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AddCommentUseCase extends UseCaseForCommand<AddCommentCommand> {

    private final DomainEventRepository repository;
    private final EventBus bus;

    @Override
    public Flux<DomainEvent> apply(Mono<AddCommentCommand> addCommentCommandMono) {
        return addCommentCommandMono.flatMapMany(command -> repository.findById(command.getPostId())
                .collectList()
                .flatMapIterable(events -> {
                    Post post = Post.from(PostId.of(command.getPostId()), events);
                    post.addAComment(
                            CommentId.of(command.getCommentId()),
                            new Name(command.getAuthor()),
                            new Content(command.getContent()),
                            ParticipantId.of(command.getParticipantId()));
                    return post.getUncommittedChanges();
                })
                .map(event -> {
                    bus.publish(event);
                    return event;
                })
                .flatMap(event -> repository.saveEvent(event))
        );

    }
}
