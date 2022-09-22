package com.alpha.postandcomments.application.adapters.repository;

import co.com.sofka.domain.generic.DomainEvent;
import com.google.gson.Gson;
import com.alpha.postandcomments.application.generic.models.StoredEvent;
import com.alpha.postandcomments.business.gateways.DomainEventRepository;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Date;

@Repository
public class MongoEventStoreRepository implements DomainEventRepository {

    //It simplifies the use of Reactive MongoDB usage and helps to avoid common errors.
    // It executes core MongoDB workflow, leaving application code to provide Document and extract results.
    // This class executes BSON queries or updates, initiating iteration over FindPublisher and catching MongoDB exceptions
    // and translating them to the generic
    private final ReactiveMongoTemplate template;

    //GSON is a Java API, developed by Google, used to convert Java objects to JSON (serialization) and JSON to Java objects (deserialization)
    private final Gson gson = new Gson();

    public MongoEventStoreRepository(ReactiveMongoTemplate template) {
        this.template = template;
    }

    @Override
    public Flux<DomainEvent> findById(String aggregateId) {
        var query = new Query(Criteria.where("aggregateRootId").is(aggregateId));
        return template.find(query, DocumentEventStored.class)
                .sort(Comparator.comparing(event -> event.getStoredEvent().getOccurredOn()))
                .map(storeEvent -> {
                    try {
                        return (DomainEvent) gson.fromJson(storeEvent.getStoredEvent().getEventBody(), Class.forName(storeEvent.getStoredEvent().getTypeName()));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        throw new IllegalStateException("couldnt find domain event");
                    }
                });
    }

    @Override
    public Mono<DomainEvent> saveEvent(DomainEvent event) {
        DocumentEventStored eventStored = new DocumentEventStored();
        eventStored.setAggregateRootId(event.aggregateRootId());
        eventStored.setStoredEvent(new StoredEvent(gson.toJson(event), new Date(), event.getClass().getCanonicalName()));
        return template.save(eventStored)
                .map(storeEvent -> {
                    try {
                        return (DomainEvent) gson.fromJson(storeEvent.getStoredEvent().getEventBody(), Class.forName(storeEvent.getStoredEvent().getTypeName()));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        throw new IllegalStateException("couldnt find domain event");
                    }
                });
    }
}
