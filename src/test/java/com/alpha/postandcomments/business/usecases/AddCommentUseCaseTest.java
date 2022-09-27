package com.alpha.postandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.alpha.postandcomments.business.gateways.DomainEventRepository;
import com.alpha.postandcomments.business.gateways.EventBus;
import com.alpha.postandcomments.domain.post.commands.AddCommentCommand;
import com.alpha.postandcomments.domain.post.events.CommentAdded;
import com.alpha.postandcomments.domain.post.events.PostCreated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AddCommentUseCaseTest {

    @Mock
    DomainEventRepository repository;
    @Mock
    AddCommentUseCase useCase;
    @Mock
    EventBus bus;

    @BeforeEach
    void init() {
        useCase = new AddCommentUseCase(repository, bus);
    }

    private final String POST_ID = "aggregateId";

    @Test
    @DisplayName("AddCommentUseCase")
    void addCommentUseCase() {

        var postCreated = new PostCreated(
                "TitleTest",
                "AuthorTest",
                "PhotoUrlTest",
                "ParticipantIdTest"
        );
        postCreated.setAggregateRootId(POST_ID);

        var commentAdded = new CommentAdded(
                "CommentIdTest",
                "CommentAuthorTest",
                "ContentTest",
                "CommentParticipantId"
        );
        commentAdded.setAggregateRootId(POST_ID);

        var command = new AddCommentCommand(
                POST_ID,
                commentAdded.getId(),
                commentAdded.getAuthor(),
                commentAdded.getContent(),
                commentAdded.getParticipantId()
        );

        Mono<DomainEvent> responseExpected = Mono.just(commentAdded);

        BDDMockito.when(repository.findById(BDDMockito.anyString()))
                .thenReturn(Flux.just(postCreated));
        BDDMockito.when(repository.saveEvent(BDDMockito.any(DomainEvent.class)))
                .thenReturn(responseExpected);

        var useCaseExecute = useCase.apply(Mono.just(command)).collectList();

        StepVerifier.create(useCaseExecute)
                .expectNextMatches(events ->
                        events.get(0).aggregateRootId().equals(POST_ID) &&
                                events.get(0) instanceof CommentAdded)
                .expectComplete().verify();
        BDDMockito.verify(repository, times(1)).findById(BDDMockito.any(String.class));
        BDDMockito.verify(repository, times(1)).saveEvent(BDDMockito.any(DomainEvent.class));
    }
}