package com.alpha.postandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.alpha.postandcomments.business.gateways.DomainEventRepository;
import com.alpha.postandcomments.business.gateways.EventBus;
import com.alpha.postandcomments.domain.participant.Participant;
import com.alpha.postandcomments.domain.participant.events.FavAdded;
import com.alpha.postandcomments.domain.participant.events.ParticipantCreated;
import com.alpha.postandcomments.domain.participant.events.commands.AddFav;
import com.alpha.postandcomments.domain.post.events.ReactionAdded;
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
class AddFavUseCaseTest {

	@Mock
	DomainEventRepository repository;

	@Mock
	AddFavUseCase useCase;

	@Mock
	EventBus bus;

	@BeforeEach
	void init() {
		useCase = new AddFavUseCase(repository, bus);
	}

	private final String PARTICIPANT = "aggregateId";

	@Test
	@DisplayName("AddFavUseCase")
	void addFavUseCase(){

		var FavAdded = new FavAdded(
		 "postId"
		);
		FavAdded.setAggregateRootId(PARTICIPANT);

		var participantCreated = new ParticipantCreated("Juan", "www","USER");
		participantCreated.setAggregateRootId(PARTICIPANT);

		var command = new AddFav(PARTICIPANT, FavAdded.getPostId());
		Mono<DomainEvent> responseExpected = Mono.just(FavAdded);


		BDDMockito.when(repository.findById(BDDMockito.anyString()))
			 .thenReturn(Flux.just(participantCreated));
		BDDMockito.when(repository.saveEvent(BDDMockito.any(DomainEvent.class)))
			 .thenReturn(responseExpected);


		var useCaseExecute = useCase.apply(Mono.just(command)).collectList();


		StepVerifier.create(useCaseExecute)
			 .expectNextMatches(events ->
					events.get(0).aggregateRootId().equals(PARTICIPANT) &&
						 events.get(0) instanceof FavAdded)
			 .expectComplete().verify();
		BDDMockito.verify(repository, times(1)).findById(BDDMockito.any(String.class));
		BDDMockito.verify(repository, times(1)).saveEvent(BDDMockito.any(DomainEvent.class));
	}

}