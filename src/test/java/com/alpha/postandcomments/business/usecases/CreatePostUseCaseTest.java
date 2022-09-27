package com.alpha.postandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.alpha.postandcomments.business.gateways.DomainEventRepository;
import com.alpha.postandcomments.business.gateways.EventBus;
import com.alpha.postandcomments.domain.post.commands.CreatePostCommand;
import com.alpha.postandcomments.domain.post.events.PostCreated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CreatePostUseCaseTest {

    @Mock
    DomainEventRepository repository;
    @Mock
    CreatePostUseCase useCase;
    @Mock
    EventBus bus;

    @BeforeEach
    void init() {
        useCase = new CreatePostUseCase(repository, bus);
    }

    private final String POST_ID = "aggregateId";

    @Test
    @DisplayName("CreatePostUseCase")
    void createPostUseCase() {
        var postCreated = new PostCreated(
                "TitleTest",
                "AuthorTest",
                "PhotoUrlTest",
                "ParticipantIdTest"
        );
        postCreated.setAggregateRootId(POST_ID);

        var command = new CreatePostCommand(
                postCreated.aggregateRootId(),
                postCreated.getName(),
                postCreated.getTitle(),
                postCreated.getPhotoUrl(),
                postCreated.getParticipantId()
        );
        Mono<DomainEvent> responseExpected = Mono.just(postCreated);

        BDDMockito.when(repository.saveEvent(BDDMockito.any(DomainEvent.class)))
                .thenReturn(responseExpected);

        var useCaseExecute = useCase.apply(Mono.just(command)).collectList();

        StepVerifier.create(useCaseExecute)
                .expectNextMatches(events ->
                        events.get(0).aggregateRootId().equals(POST_ID)
                        && events.get(0) instanceof PostCreated)
                .expectComplete().verify();
        BDDMockito.verify(repository, times(1))
                .saveEvent(BDDMockito.any(DomainEvent.class));
    }

}