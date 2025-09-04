package com.crediya.autenticacion.r2dbc.repositories;

import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.model.user.gateways.UserRepository;
import com.crediya.autenticacion.r2dbc.entities.UserEntity;
import com.crediya.autenticacion.r2dbc.helper.ReactiveAdapterOperations;
import com.crediya.autenticacion.r2dbc.mappers.UserEntityMapper;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
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
        return super.repository
                .existsByEmail(email.trim().toLowerCase());
    }

    @Override
    public Mono<Boolean> existsByIdNumber(String idNumber) {
        return super.repository
                .existsByIdNumber(idNumber.trim().toUpperCase());
    }

    @Override
    public Mono<User> findByIdNumber(String idNumber) {
        return super.repository
                .findByIdNumber(idNumber.trim())
                .map(UserEntityMapper::toUser);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return super.repository
                .findByEmail(email.trim().toLowerCase())
                .map(UserEntityMapper::toUser);
    }

    @Override
    public Mono<User> findById(BigInteger id) {
        return super.repository
                .findById(id)
                .map(UserEntityMapper::toUser);
    }

    @Override
    public Mono<User> save(User user) {
        return super.repository
                .save(UserEntityMapper.toUserEntity(user))
                .map(UserEntityMapper::toUser);
    }

    @Override
    public Flux<User> findAll() {
        return super.repository
                .findAll()
                .map(UserEntityMapper::toUser);
    }

}
