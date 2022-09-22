package com.alpha.postandcomments.application.config.jwt;


import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter implements WebFilter {
    //Bearer
    public static final String HEADER_PREFIX ="Bearer ";

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        //1. Get the request
        var token = resolveToken(exchange.getRequest());
        if(StringUtils.hasText(token) && this.jwtTokenProvider.validateToken(token)){
            var authenticantion = this.jwtTokenProvider.getAuthentication(token);
            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authenticantion));
        }
       return chain.filter(exchange);

    }

    private String resolveToken(ServerHttpRequest request){
        //1. Get the header that has the token
        var bearerToken= request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION); //Bearer jfhkjdsgfkjsdgfkjsgdfgsjkfgsjkdg
        //2.
        return StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX) ?
               bearerToken.substring(7) : null;

    }
}
