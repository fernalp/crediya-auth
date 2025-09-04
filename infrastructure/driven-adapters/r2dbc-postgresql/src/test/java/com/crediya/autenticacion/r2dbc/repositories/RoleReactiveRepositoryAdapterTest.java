package com.crediya.autenticacion.r2dbc.repositories;

import com.crediya.autenticacion.model.role.Role;
import com.crediya.autenticacion.r2dbc.entities.RoleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleReactiveRepositoryAdapterTest {

    @InjectMocks
    private RoleReactiveRepositoryAdapter roleRepositoryAdapter;

    @Mock
    private RoleReactiveRepository roleRepository;

    @Mock
    private ObjectMapper mapper;

    private RoleEntity fakeRoleEntity;
    private Role fakeRole;

    @BeforeEach
    void setUp() {
        fakeRoleEntity = new RoleEntity(3, "CUSTOMER", "customer");
        fakeRole = new Role(3, "CUSTOMER", "customer");
    }
    @Test
    void shouldRoleWhenFindValueByName() {
        String roleName = "CUSTOMER";
        when(roleRepository.findByName(roleName.trim().toUpperCase())).thenReturn(Mono.just(fakeRoleEntity));
        when(mapper.map(fakeRoleEntity, Role.class)).thenReturn(fakeRole);


        Mono<Role> result = roleRepositoryAdapter.findByName(roleName);

        StepVerifier.create(result)
                .expectNext(fakeRole)
                .verifyComplete();
    }
}
