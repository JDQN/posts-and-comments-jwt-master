package com.alpha.postandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.alpha.postandcomments.business.gateways.DomainEventRepository;
import com.alpha.postandcomments.business.gateways.EventBus;
import com.alpha.postandcomments.business.generic.UseCaseForCommand;
import com.alpha.postandcomments.domain.commons.values.Content;
import com.alpha.postandcomments.domain.commons.values.Name;
import com.alpha.postandcomments.domain.commons.values.ParticipantId;
import com.alpha.postandcomments.domain.participant.Participant;
import com.alpha.postandcomments.domain.participant.commands.ReceiveMessage;
import com.alpha.postandcomments.domain.participant.values.MessageId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ReceiveMessageUseCase extends UseCaseForCommand<ReceiveMessage> {

    private final DomainEventRepository repository;
    private final EventBus bus;


    @Override
    public Flux<DomainEvent> apply(Mono<ReceiveMessage> receiveMessageMono) {
        return receiveMessageMono.flatMapMany(command -> repository.findById(command.getParticipantId())
                .collectList()
                .flatMapIterable(events -> {
                    Participant participant = Participant.from(ParticipantId.of(command.getParticipantId()), events);
                    participant.receiveMessage(
                            MessageId.of(command.getMessageId()),
                            new Name(command.getName()),
                            new Content(command.getContent())
                    );
                    return participant.getUncommittedChanges();
                })
                .map(domainEvent -> {
                    bus.publish(domainEvent);
                    return domainEvent;
                })
                .flatMap(domainEvent -> repository.saveEvent(domainEvent))
        );
    }
}
