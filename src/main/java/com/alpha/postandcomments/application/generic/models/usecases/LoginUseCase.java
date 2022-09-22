package com.alpha.postandcomments.application.generic.models.usecases;

import com.alpha.postandcomments.application.config.jwt.JwtTokenProvider;
import com.alpha.postandcomments.application.generic.models.AuthenticationRequest;
import com.alpha.postandcomments.application.generic.models.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginUseCase {
    private final JwtTokenProvider jwtTokenProvider;

    private final ReactiveAuthenticationManager authenticationManager;

    public Mono<ServerResponse> logIn(Mono<AuthenticationRequest> authenticationRequest){
        return authenticationRequest
					.flatMap(authRequest -> this.authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()))
					.onErrorMap(BadCredentialsException.class, err -> new Throwable(HttpStatus.FORBIDDEN.toString()))
					.map(this.jwtTokenProvider::createToken))

					.flatMap(jwt -> {
						//HttpHeaders httpHeaders = new HttpHeaders();
						//httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
//                    var tokenBody = Map.of("access_token", jwt);
						return ServerResponse
							.ok()
							.bodyValue(new TokenResponse(jwt));

					});
    }
}
