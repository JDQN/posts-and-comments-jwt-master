package com.alpha.postandcomments.business.usecases;
import co.com.sofka.domain.generic.DomainEvent;
import com.alpha.postandcomments.business.gateways.DomainEventRepository;
import com.alpha.postandcomments.business.gateways.EventBus;
import com.alpha.postandcomments.domain.participant.events.EventCasted;
import com.alpha.postandcomments.domain.participant.events.ParticipantCreated;
import com.alpha.postandcomments.domain.participant.events.commands.CastEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CastEventUseCaseTest {

    @Mock
    DomainEventRepository repository;
    @Mock
    CastEventUseCase useCase;
    @Mock
    EventBus bus;

    @BeforeEach
    void init() {
        useCase = new CastEventUseCase(repository, bus);
    }

    private final String PARTICIPANT_ID = "aggregateId";

    @Test
    @DisplayName("CastEventUseCaseTest")
    void castEventUseCaseTest(){
        /*
        * Assert
        * */
        var command = new CastEvent(
                "idevento",
                PARTICIPANT_ID,
                "23/08/2022",
                "Canal",
                "Creado",
                "Post Agregado"
        );
        BDDMockito.when(repository.findById(BDDMockito.anyString()))
                .thenReturn(history());
        BDDMockito.when(repository.saveEvent(BDDMockito.any(DomainEvent.class)))
                .thenReturn(eventsToSave());

        /*
        * Act
        * */
        var useCaseExecute = useCase.apply(Mono.just(command)).collectList();

        /*
        Assert
        * */
        StepVerifier.create(useCaseExecute)
                .expectNextMatches(events ->
                        events.get(0).aggregateRootId().equals(PARTICIPANT_ID) &&
                                events.get(0) instanceof EventCasted &&
                                ((EventCasted) events.get(0)).getElement().equals("Canal"))
                .expectComplete().verify();
        Mockito.verify(repository, times(1)).findById(Mockito.any(String.class));
        Mockito.verify(repository, times(1)).saveEvent(Mockito.any(DomainEvent.class));

    }

    private Flux<DomainEvent> history(){
        var participantCreated = new ParticipantCreated(
                "Juan Quimbayo",
                "src/photo.jpg",
                "USER"
        );
        participantCreated.setAggregateRootId(PARTICIPANT_ID);
        return Flux.just(participantCreated);
    }

    private Mono<DomainEvent> eventsToSave(){
        var eventCasted = new EventCasted(
                "idevento",
                "23/08/2022",
                "Canal",
                "Creado",
                "Post Agregado"
                );
        eventCasted.setAggregateRootId(PARTICIPANT_ID);
        return Mono.just(eventCasted);
    }

}