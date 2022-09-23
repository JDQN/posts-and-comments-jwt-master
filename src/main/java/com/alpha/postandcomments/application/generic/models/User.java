package com.alpha.postandcomments.application.generic.models;

import lombok.*;
import org.apache.commons.validator.GenericValidator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@Document(collection="User")
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    private String email;

    public User(String id, String username, String password, String email, boolean active, List<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.active = active;
        this.roles = roles;

        if(!GenericValidator.isEmail(email)){
            throw new IllegalArgumentException("Invalid email format");
        }


    }

    @Builder.Default()
    private boolean active = true;
    @Builder.Default()
    private List<String> roles = new ArrayList<>();

}
