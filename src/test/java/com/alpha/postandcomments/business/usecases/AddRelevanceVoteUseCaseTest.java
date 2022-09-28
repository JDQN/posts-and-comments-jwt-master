package com.alpha.postandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.alpha.postandcomments.business.gateways.DomainEventRepository;
import com.alpha.postandcomments.business.gateways.EventBus;
import com.alpha.postandcomments.domain.post.commands.AddRelevanceVoteCommand;
import com.alpha.postandcomments.domain.post.events.PostCreated;
import com.alpha.postandcomments.domain.post.events.ReactionAdded;
import com.alpha.postandcomments.domain.post.events.RelevanceVoteAdded;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AddRelevanceVoteUseCaseTest {

	@Mock
	DomainEventRepository repository;

	@Mock
	AddRelevanceVoteUseCase useCase;

	@Mock
	EventBus bus;

	@BeforeEach
	void init() {
		useCase = new AddRelevanceVoteUseCase(repository, bus);
	}

	private final String POST_ID = "aggregateId";

	@Test
	@DisplayName("AddRelevanceVoteUseCase")
	void addRelevanceVoteUseCase() {

		var addRelevanceVote = new PostCreated(
			 "TitleTest",
			 "AuthorTest",
			 "PhotoUrlTest",
			 "ParticipantIdTest"
		);
		addRelevanceVote.setAggregateRootId(POST_ID);

		var relevanceVoteAdded = new RelevanceVoteAdded("Vote");
		relevanceVoteAdded.setAggregateRootId(POST_ID);

		var command = new AddRelevanceVoteCommand(POST_ID);
		Mono<DomainEvent> responseExpected = Mono.just(relevanceVoteAdded);


		BDDMockito.when(repository.findById(BDDMockito.anyString()))
			 .thenReturn(Flux.just(addRelevanceVote));
		BDDMockito.when(repository.saveEvent(BDDMockito.any(DomainEvent.class)))
			 .thenReturn(responseExpected);

		var useCaseExecute = useCase.apply(Mono.just(command)).collectList();

		StepVerifier.create(useCaseExecute)
			 .expectNextMatches(events ->
					events.get(0).aggregateRootId().equals(POST_ID) &&
						 events.get(0) instanceof RelevanceVoteAdded)
			 .expectComplete().verify();
		BDDMockito.verify(repository, times(1)).findById(BDDMockito.any(String.class));
		BDDMockito.verify(repository, times(1)).saveEvent(BDDMockito.any(DomainEvent.class));
	}

}