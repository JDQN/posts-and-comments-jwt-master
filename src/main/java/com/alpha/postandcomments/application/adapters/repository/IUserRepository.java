package com.alpha.postandcomments.application.adapters.repository;

import com.alpha.postandcomments.application.generic.models.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IUserRepository extends ReactiveMongoRepository<User,String> {
    Mono<User> findByUsername(String username);
}
