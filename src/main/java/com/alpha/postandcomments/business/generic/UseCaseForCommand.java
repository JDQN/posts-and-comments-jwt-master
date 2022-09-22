package com.alpha.postandcomments.business.generic;

import co.com.sofka.domain.generic.Command;
import co.com.sofka.domain.generic.DomainEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

//¿?: You declare an abstract class to encapsulate UseCases related to Commands (that's the R extends) and this instances will receibe a Mono<R> and return a Flu<DomainEvent> (related to the Function implementation)
public abstract class  UseCaseForCommand <R extends Command> implements Function<Mono<R>, Flux<DomainEvent>> {
    public abstract Flux<DomainEvent> apply(Mono<R> rMono);
}
