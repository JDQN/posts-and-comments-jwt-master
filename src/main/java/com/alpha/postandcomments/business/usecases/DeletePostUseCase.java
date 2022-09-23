package com.alpha.postandcomments.business.usecases;


import co.com.sofka.domain.generic.DomainEvent;
import com.alpha.postandcomments.business.gateways.DomainEventRepository;
import com.alpha.postandcomments.business.gateways.EventBus;
import com.alpha.postandcomments.business.generic.UseCaseForCommand;
import com.alpha.postandcomments.domain.commons.values.PostId;
import com.alpha.postandcomments.domain.post.Post;
import com.alpha.postandcomments.domain.post.commands.DeletePostCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DeletePostUseCase extends UseCaseForCommand<DeletePostCommand> {

    private final DomainEventRepository repository;
    private final EventBus bus;


    @Override
    public Flux<DomainEvent> apply(Mono<DeletePostCommand> deletePostCommandMono) {
        return deletePostCommandMono.flatMapMany(command -> repository.findById(command.getPostId())
                .collectList()
                .flatMapIterable( events -> {
                    Post post = Post.from(PostId.of(command.getPostId()),events);
                    post.deletePost(PostId.of(command.getPostId()));
                    return post.getUncommittedChanges();
                })
                .map(event ->{
                    bus.publish(event);
                    return event;
                })
                .flatMap(event -> repository.saveEvent(event))
        );
    }
}
