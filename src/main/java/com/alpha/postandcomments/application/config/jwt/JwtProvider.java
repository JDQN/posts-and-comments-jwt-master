package com.alpha.postandcomments.application.config.jwt;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JwtProvider {

    private String secretKey="god-saved-the-queen-of-uk";

    //In milliseconds
    private long validTime= 3600000; //1h*
}
