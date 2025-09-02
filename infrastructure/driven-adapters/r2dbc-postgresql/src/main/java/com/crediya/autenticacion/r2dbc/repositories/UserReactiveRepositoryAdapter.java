package com.crediya.autenticacion.r2dbc.repositories;

import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.model.user.gateways.UserRepository;
import com.crediya.autenticacion.r2dbc.entities.UserEntity;
import com.crediya.autenticacion.r2dbc.helper.ReactiveAdapterOperations;
import com.crediya.autenticacion.r2dbc.mappers.UserEntityMapper;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
        BigInteger,
        UserReactiveRepository
> implements UserRepository {
    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, User.class));
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repository.existsByEmail(email.toLowerCase());
    }

    @Override
    public Mono<Boolean> existsByIdNumber(String idNumber) {
        return repository.existsByIdNumber(idNumber);
    }

    @Override
    public Mono<User> findByIdNumber(String idNumber) {
        return repository.findByIdNumber(idNumber).map(UserEntityMapper::toUser);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return repository.findByEmail(email.toLowerCase()).map(UserEntityMapper::toUser);
    }



}
