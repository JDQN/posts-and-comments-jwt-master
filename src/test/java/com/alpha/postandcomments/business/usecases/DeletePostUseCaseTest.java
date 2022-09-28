package com.alpha.postandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.alpha.postandcomments.business.gateways.DomainEventRepository;
import com.alpha.postandcomments.business.gateways.EventBus;
import com.alpha.postandcomments.domain.post.commands.DeletePostCommand;
import com.alpha.postandcomments.domain.post.events.PostCreated;
import com.alpha.postandcomments.domain.post.events.PostDeleted;
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
class DeletePostUseCaseTest {

    @Mock
    DomainEventRepository repository;

    @Mock
    EventBus bus;

    @Mock
    DeletePostUseCase useCase;

    @BeforeEach
    void init(){
        useCase = new DeletePostUseCase(repository,bus);
    }

    private final String POST_ID = "aggregateId";

    @Test
    @DisplayName("AddPostUseCase")
    void deletePostUseCase(){

        var postCreated = new PostCreated(
                "TitleTest",
                "AuthorTest",
                "PhotoUrlTest",
                "ParticipantIdTest"
        );
        postCreated.setAggregateRootId(POST_ID);

        var postDeleted = new PostDeleted(POST_ID);
        postDeleted.setAggregateRootId(POST_ID);

        var command = new DeletePostCommand(POST_ID);
        Mono<DomainEvent> responseExpected = Mono.just(postDeleted);

        BDDMockito.when(repository.findById(BDDMockito.anyString()))
                .thenReturn(Flux.just(postCreated,postCreated));
        BDDMockito.when(repository.saveEvent(BDDMockito.any(DomainEvent.class)))
                .thenReturn(responseExpected);

        var useCaseExecute = useCase.apply(Mono.just(command)).collectList();

        StepVerifier.create(useCaseExecute)
                .expectNextMatches(events ->
                        events.get(0).aggregateRootId().equals(POST_ID) &&
                                events.get(0) instanceof PostDeleted)
                .expectComplete().verify();
        Mockito.verify(repository, times(1)).findById(Mockito.any(String.class));
        Mockito.verify(repository, times(1)).saveEvent(Mockito.any(DomainEvent.class));

    }


}