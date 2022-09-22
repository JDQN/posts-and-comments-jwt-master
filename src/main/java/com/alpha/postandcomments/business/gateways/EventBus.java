package com.alpha.postandcomments.business.gateways;

import co.com.sofka.domain.generic.DomainEvent;

//¿?: This interface it just to manage the publish operation that we will do to rabbitmq
public interface EventBus {
    void publish(DomainEvent event);

    void publishError(Throwable errorEvent);
}
