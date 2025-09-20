package com.crediya.autenticacion.security.services;

import com.crediya.autenticacion.model.role.gateways.RoleRepository;
import com.crediya.autenticacion.model.token.Token;
import com.crediya.autenticacion.model.token.gateways.AuthRepository;
import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.model.user.gateways.UserRepository;
import com.crediya.autenticacion.security.config.jwt.model.RoleJWT;
import com.crediya.autenticacion.security.config.jwt.model.UserJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomReactiveUserDetailsService implements AuthRepository, ReactiveUserDetailsService {

    private static final String MESSAGE_USER_NOT_FOUND = "El usuario no se encontró";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtProvider jwtProvider;

    @Override
    public Mono<Token> login(String email, String password, User user) {
        return jwtProvider.generateToken(email, user.getRole().getName())
                .map(tokenString -> Token.builder()
                            .accessToken(tokenString)
                            .tokenType("Bearer")
                            .build());
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByEmail(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException(MESSAGE_USER_NOT_FOUND)))
                .flatMap(user -> roleRepository.findById(user.getRole().getId()).map(role -> {
                    user.setRole(role);
                    return user;
                }))
                .map(user -> UserJWT.builder()
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .role(RoleJWT.builder().name(user.getRole().getName()).build())
                        .build());
    }
}
