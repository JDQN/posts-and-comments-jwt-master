package com.alpha.postandcomments.business.usecases;
import co.com.sofka.domain.generic.DomainEvent;
import com.alpha.postandcomments.business.gateways.DomainEventRepository;
import com.alpha.postandcomments.business.gateways.EventBus;
import com.alpha.postandcomments.domain.participant.events.MessageReceived;
import com.alpha.postandcomments.domain.participant.events.ParticipantCreated;
import com.alpha.postandcomments.domain.participant.events.commands.ReceiveMessage;
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
class ReceiveMessageUseCaseTest {

    @Mock
    DomainEventRepository repository;
    @Mock
    EventBus bus;
    @Mock
    ReceiveMessageUseCase useCase;

    private final String PARTICIPANT_ID = "aggregateId";

    @BeforeEach
    void init(){
        useCase = new ReceiveMessageUseCase(repository,bus);
    }

    @Test
    @DisplayName("ReceiveMessageUseCase")
    void receiveMessageUseCaseTest(){
        /*
        Arrange
        * */
        var command = new ReceiveMessage(
                "message01",
                "44567",
                "Carlos Estupiñan",
                "Hola buenos dias,¿Como estas?"
        );
        BDDMockito.when(repository.findById(BDDMockito.anyString()))
                .thenReturn(history());
        BDDMockito.when(repository.saveEvent(BDDMockito.any(DomainEvent.class)))
                .thenReturn(eventsToSave());

        /*
        Act
        * */
        var useCaseExecute = useCase.apply(Mono.just(command)).collectList();

        /*
        Assert
        * */
        StepVerifier.create(useCaseExecute)
                .expectNextMatches(events ->
                                events.get(0).aggregateRootId().equals(PARTICIPANT_ID) &&
                                events.get(0) instanceof MessageReceived &&
                                ((MessageReceived) events.get(0)).getMessageId().equals("message01"))
                .expectComplete().verify();
        Mockito.verify(repository, times(1)).findById(Mockito.any(String.class));
        Mockito.verify(repository, times(1)).saveEvent(Mockito.any(DomainEvent.class));

    }


    private Flux<DomainEvent> history(){
        var participantCreated = new ParticipantCreated(
                "Andres Benitez",
                "src/photo.jpg",
                "USER"
        );
        participantCreated.setAggregateRootId(PARTICIPANT_ID);
        return Flux.just(participantCreated);
    }

    private Mono<DomainEvent> eventsToSave(){
        var messageReceived = new MessageReceived(
                "message01"
                  ,"Carlos Estupiñan",
                 "Hola buenos dias,¿Como estas?");
        messageReceived.setAggregateRootId(PARTICIPANT_ID);
        return Mono.just(messageReceived);
    }

}