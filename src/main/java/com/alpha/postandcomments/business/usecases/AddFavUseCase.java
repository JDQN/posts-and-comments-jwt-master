package com.alpha.postandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.alpha.postandcomments.business.gateways.DomainEventRepository;
import com.alpha.postandcomments.business.gateways.EventBus;
import com.alpha.postandcomments.business.generic.UseCaseForCommand;
import com.alpha.postandcomments.domain.commons.values.ParticipantId;
import com.alpha.postandcomments.domain.commons.values.PostId;
import com.alpha.postandcomments.domain.participant.Participant;
import com.alpha.postandcomments.domain.participant.events.commands.AddFav;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AddFavUseCase extends UseCaseForCommand<AddFav> {

    private final DomainEventRepository repository;
    private final EventBus bus;


    @Override
    public Flux<DomainEvent> apply(Mono<AddFav> addFavMono) {
        return addFavMono.flatMapMany(command -> repository.findById(command.getParticipantId())
                .collectList()
                .flatMapIterable(events -> {
                    Participant participant = Participant.from(ParticipantId.of(command.getParticipantId()),events);
                    participant.addFav(PostId.of(command.getPostId()));
                    return participant.getUncommittedChanges();
                })
                .map(event ->
                {
                    bus.publish(event);
                    return event;
                })
                .flatMap(event -> repository.saveEvent(event))
        );
    }
}
