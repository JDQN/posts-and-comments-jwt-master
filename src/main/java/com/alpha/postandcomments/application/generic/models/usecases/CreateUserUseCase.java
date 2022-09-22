package com.alpha.postandcomments.application.generic.models.usecases;

import com.alpha.postandcomments.application.adapters.repository.IUserRepository;
import com.alpha.postandcomments.application.generic.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public Mono<User> save(User u, String role){

			return this.userRepository
				.save(u.toBuilder()
				.password(passwordEncoder.encode(u.getPassword()))
				.email(u.getUsername()+"@mail.com")
				.roles(new ArrayList<>(){{add(role);}}).build());
	}

}
