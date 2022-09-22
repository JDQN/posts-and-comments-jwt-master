package com.alpha.postandcomments.application.generic.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AuthenticationRequest implements Serializable {
    private static final long serialVersionUID=2L;

    String username;
    String password;
}
