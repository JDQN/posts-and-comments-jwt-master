package com.alpha.postandcomments.business.usecases;


import co.com.sofka.domain.generic.DomainEvent;
import com.alpha.postandcomments.business.gateways.DomainEventRepository;
import com.alpha.postandcomments.business.gateways.EventBus;
import com.alpha.postandcomments.domain.participant.events.ParticipantCreated;
import com.alpha.postandcomments.domain.participant.events.commands.CreateParticipantCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CreateParticipantUseCaseTest {

    @Mock
    DomainEventRepository repository;
    @Mock
    CreateParticipantUseCase useCase;
    @Mock
    EventBus bus;

    @BeforeEach
    void init() {
        useCase = new CreateParticipantUseCase(repository, bus);
    }

    private final String PARTICIPANT_ID = "aggregateId";

    @Test
    @DisplayName("CreateParticipantUseCase")
    void createParticipantUseCase(){
        var participantCreated = new ParticipantCreated(
                "Usuario",
                "uri/com",
                "USER"
        );
        participantCreated.setAggregateRootId(PARTICIPANT_ID);

        var command = new CreateParticipantCommand(
                participantCreated.aggregateRootId(),
                participantCreated.getName(),
                participantCreated.getPhotoUrl(),
                participantCreated.getRol()
        );
        Mono<DomainEvent> responseExpected = Mono.just(participantCreated);

        BDDMockito.when(repository.saveEvent(BDDMockito.any(DomainEvent.class)))
                .thenReturn(responseExpected);

        var useCaseExecute = useCase.apply(Mono.just(command)).collectList();

        StepVerifier.create(useCaseExecute)
                .expectNextMatches(events ->
                        events.get(0).aggregateRootId().equals(PARTICIPANT_ID)
                                && events.get(0) instanceof ParticipantCreated)
                .expectComplete().verify();
        BDDMockito.verify(repository, times(1))
                .saveEvent(BDDMockito.any(DomainEvent.class));

    }
}