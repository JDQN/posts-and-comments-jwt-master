package com.alpha.postandcomments.application.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    //1. createToken - checked
    //2. validateToken
    //3. autenticate

    private static final String AUTHORITIES_KEY="roles";

    private final JwtProvider jwtProvider;

    private SecretKey secretKey;

    @PostConstruct
    protected  void init(){
        var secret = Base64.getEncoder().encodeToString(jwtProvider.getSecretKey().getBytes());
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Authentication authentication){
        var username = authentication.getName();
        var authorites = authentication.getAuthorities();

        //Creating a Claim instance in order to send it and gets our access token
        var claims = Jwts.claims().setSubject(username);

        if(!authorites.isEmpty()){
            claims.put(AUTHORITIES_KEY,authorites.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
        }

        //Setting the expiring time of the token
        var nowDate = new Date(); //Actual date
        var validDate = new Date(nowDate.getTime() + this.jwtProvider.getValidTime());

        //Generating the token
        return Jwts.builder()
					.setClaims(claims)
					.setIssuedAt(nowDate)
					.setExpiration(validDate)
					.signWith(this.secretKey, SignatureAlgorithm.HS256)
					.compact();
    }

    public Authentication getAuthentication(String token){
        //Extract and prepare the Claims instance that has the information of the token to be authenticated
        var claims = Jwts.parserBuilder()
					.setSigningKey(this.secretKey)
					.build()
					.parseClaimsJws(token)
					.getBody();

        var authoritiesClaims = claims.get(AUTHORITIES_KEY);

        var authorities = authoritiesClaims != null ? AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaims.toString())
                : AuthorityUtils.NO_AUTHORITIES;

        //User
        var principal = new User(claims.getSubject(), "",authorities);

        return new UsernamePasswordAuthenticationToken(principal,"",authorities);

    }

    public boolean validateToken(String token){
        //call the claims
        try{
            var claims = Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token);
            log.info("Token is ok: Expiration time is "+claims.getBody().getExpiration().toString());
            return true;
        }catch(JwtException | IllegalArgumentException e){
            log.info("Invalid token: "+e.getMessage());
        }
        return false;

    }

}
