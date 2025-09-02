package com.crediya.autenticacion.model.user.gateways;

import com.crediya.autenticacion.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<Boolean> existsByEmail(String email);
    Mono<Boolean> existsByIdNumber(String idNumber);
    Mono<User> findByIdNumber(String idNumber);
    Mono<User> findByEmail(String email);
    Mono<User> save(User user);
    Flux<User> findAll();
}
