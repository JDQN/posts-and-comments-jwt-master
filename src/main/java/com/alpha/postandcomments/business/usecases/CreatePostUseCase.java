package com.alpha.postandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.alpha.postandcomments.business.gateways.EventBus;
import com.alpha.postandcomments.business.generic.UseCaseForCommand;
import com.alpha.postandcomments.domain.Post;
import com.alpha.postandcomments.domain.values.Title;
import com.alpha.postandcomments.business.gateways.DomainEventRepository;
import com.alpha.postandcomments.domain.commands.CreatePostCommand;
import com.alpha.postandcomments.domain.values.Author;
import com.alpha.postandcomments.domain.values.PostId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CreatePostUseCase extends UseCaseForCommand<CreatePostCommand> {
    private final DomainEventRepository repository;
    private final EventBus bus;

    @Override
    public Flux<DomainEvent> apply(Mono<CreatePostCommand> createPostCommandMono) {
        return createPostCommandMono.flatMapIterable(command -> {
            Post post = new Post(PostId.of(command.getPostId()), new Title(command.getTitle()), new Author(command.getAuthor()));
            return post.getUncommittedChanges();
        }).flatMap(event ->
                repository.saveEvent(event).thenReturn(event)).doOnNext(bus::publish);
    }
}
