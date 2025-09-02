package com.crediya.autenticacion.model.role.gateways;

import com.crediya.autenticacion.model.role.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleRepository {

    Flux<Role> findAll();
    Mono<Role> findById(Integer id);
    Mono<Role> findByName(String name);


}
