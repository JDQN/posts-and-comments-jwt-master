package com.alpha.postandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.alpha.postandcomments.business.gateways.DomainEventRepository;
import com.alpha.postandcomments.business.gateways.EventBus;
import com.alpha.postandcomments.business.generic.UseCaseForCommand;
import com.alpha.postandcomments.domain.commons.values.Name;
import com.alpha.postandcomments.domain.commons.values.ParticipantId;
import com.alpha.postandcomments.domain.commons.values.PhotoUrl;
import com.alpha.postandcomments.domain.participant.Participant;
import com.alpha.postandcomments.domain.participant.commands.CreateParticipantCommand;
import com.alpha.postandcomments.domain.participant.values.Rol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CreateParticipantUseCase extends UseCaseForCommand<CreateParticipantCommand> {

    private final DomainEventRepository repository;
    private final EventBus bus;

    @Override
    public Flux<DomainEvent> apply(Mono<CreateParticipantCommand> createParticipantCommandMono) {
        return createParticipantCommandMono.flatMapIterable(command -> {
                            Participant participant = new Participant(
                                    ParticipantId.of(command.getParticipantId()),
                                    new Name(command.getName()),
                                    new PhotoUrl(command.getPhotoUrl()),
                                    new Rol(command.getRol()));
                            return participant.getUncommittedChanges();
                        }
                )
                .flatMap(event -> repository.saveEvent(event).thenReturn(event))
                .doOnNext(bus::publish);
    }


}
