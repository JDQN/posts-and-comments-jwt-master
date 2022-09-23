package com.alpha.postandcomments.application.handlers;


import co.com.sofka.domain.generic.DomainEvent;
import com.alpha.postandcomments.business.usecases.AddCommentUseCase;
import com.alpha.postandcomments.business.usecases.CreatePostUseCase;
import com.alpha.postandcomments.domain.post.commands.AddCommentCommand;
import com.alpha.postandcomments.domain.post.commands.CreatePostCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
//A router that has creation of posts and to add comments
@Configuration
public class CommandHandler {

    @Bean
    public RouterFunction<ServerResponse> createPost(CreatePostUseCase useCase){

        return route(
                POST("/create/post").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.apply(request.bodyToMono(CreatePostCommand.class)), DomainEvent.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> addComment(AddCommentUseCase useCase){

        return route(
						POST("/add/comment").and(accept(MediaType.APPLICATION_JSON)),
						request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
								.body(BodyInserters.fromPublisher(useCase.apply(request.bodyToMono(AddCommentCommand.class)), DomainEvent.class))
        );
    }
}
