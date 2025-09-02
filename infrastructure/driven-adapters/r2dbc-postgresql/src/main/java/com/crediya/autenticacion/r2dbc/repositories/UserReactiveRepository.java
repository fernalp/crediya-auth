package com.crediya.autenticacion.r2dbc.repositories;

import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.r2dbc.entities.UserEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface UserReactiveRepository extends ReactiveCrudRepository<UserEntity, BigInteger>, ReactiveQueryByExampleExecutor<UserEntity> {
    
    Mono<UserEntity> findByEmail(String email);
    Mono<UserEntity> findByIdNumber(String idNumber);

    Mono<Boolean> existsByEmail(String email);
    Mono<Boolean> existsByIdNumber(String idNumber);
}

