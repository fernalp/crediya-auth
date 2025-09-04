package com.crediya.autenticacion.r2dbc.repositories;

import com.crediya.autenticacion.model.role.Role;
import com.crediya.autenticacion.model.role.gateways.RoleRepository;
import com.crediya.autenticacion.r2dbc.entities.RoleEntity;
import com.crediya.autenticacion.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class RoleReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Role,
        RoleEntity,
        Integer,
        RoleReactiveRepository
> implements RoleRepository {
    public RoleReactiveRepositoryAdapter(RoleReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Role.class));
    }

    @Override
    public Mono<Role> findByName(String name) {
        return super.repository
                .findByName(name.trim().toUpperCase())
                .map(this::toEntity);
    }
}
