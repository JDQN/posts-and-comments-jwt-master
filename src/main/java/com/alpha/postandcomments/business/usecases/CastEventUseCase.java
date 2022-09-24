package com.alpha.postandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.alpha.postandcomments.business.gateways.DomainEventRepository;
import com.alpha.postandcomments.business.gateways.EventBus;
import com.alpha.postandcomments.business.generic.UseCaseForCommand;
import com.alpha.postandcomments.domain.commons.values.ParticipantId;
import com.alpha.postandcomments.domain.participant.Participant;
import com.alpha.postandcomments.domain.participant.commands.CastEvent;
import com.alpha.postandcomments.domain.participant.values.DateOfEvent;
import com.alpha.postandcomments.domain.participant.values.Detail;
import com.alpha.postandcomments.domain.participant.values.Element;
import com.alpha.postandcomments.domain.participant.values.EventId;
import com.alpha.postandcomments.domain.participant.values.TypeOfEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CastEventUseCase extends UseCaseForCommand<CastEvent> {

    private final DomainEventRepository repository;
    private final EventBus bus;


    @Override
    public Flux<DomainEvent> apply(Mono<CastEvent> castEventMono) {
        return castEventMono.flatMapMany(command -> repository.findById(command.getParticipantId())
                .collectList()
                .flatMapIterable(events -> {
                    Participant participant = Participant.from(ParticipantId.of(command.getParticipantId()), events);
                    participant.castEvent(
                            EventId.of(command.getEventId()),
                            new DateOfEvent(command.getDate()),
                            new Element(command.getElement()),
                            new TypeOfEvent(command.getTypeOfEvent()),
                            new Detail(command.getDetail())
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
