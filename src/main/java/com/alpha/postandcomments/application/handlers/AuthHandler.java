package com.alpha.postandcomments.application.handlers;

import com.alpha.postandcomments.application.generic.models.AuthenticationRequest;
import com.alpha.postandcomments.application.generic.models.User;
import com.alpha.postandcomments.application.generic.models.usecases.CreateUserUseCase;
import com.alpha.postandcomments.application.generic.models.usecases.LoginUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AuthHandler {


    @Bean
    RouterFunction<ServerResponse> createUser(CreateUserUseCase createUserUseCase){
			return route(POST("/auth/save/{role}"),
				request -> request.bodyToMono(User.class)
					.flatMap(user -> request.pathVariable("role").equals("admin")?
						createUserUseCase.save(user,"ROLE_ADMIN"):
						createUserUseCase.save(user,"ROLE_USER"))
					.flatMap(user -> ServerResponse.status(HttpStatus.CREATED).bodyValue(user)));
    }

    @Bean
    RouterFunction<ServerResponse> loginRouter(LoginUseCase loginUseCase){
			return route(POST("/auth/login"),
				request -> loginUseCase.logIn(request.bodyToMono(AuthenticationRequest.class))
				/*.onErrorResume(throwable -> ServerResponse.status(HttpStatus.FORBIDDEN).build())*/);
    }


}
