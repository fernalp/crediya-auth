package com.crediya.autenticacion.r2dbc;

import com.crediya.autenticacion.model.role.Role;
import com.crediya.autenticacion.r2dbc.entities.RoleEntity;
import com.crediya.autenticacion.r2dbc.entities.UserEntity;
import com.crediya.autenticacion.r2dbc.repositories.RoleReactiveRepository;
import com.crediya.autenticacion.r2dbc.repositories.RoleReactiveRepositoryAdapter;
import com.crediya.autenticacion.r2dbc.repositories.UserReactiveRepository;
import com.crediya.autenticacion.r2dbc.repositories.UserReactiveRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleReactiveRepositoryAdapterTest {

    @InjectMocks
    RoleReactiveRepositoryAdapter roleRepositoryAdapter;

    @InjectMocks
    UserReactiveRepositoryAdapter userRepositoryAdapter;

    @Mock
    RoleReactiveRepository roleRepository;

    @Mock
    UserReactiveRepository userRepository;

    @Mock
    ObjectMapper mapper;
//
//    UserEntity userEntity;
//    RoleEntity roleEntity;
//
//    @BeforeEach
//    void setUp() {
//        userEntity = new UserEntity(
//                BigInteger.ONE,
//                "123456789",
//                "fernando",
//                "almanza",
//                LocalDate.parse("2020-01-01"),
//                "address",
//                "3213213211",
//                "email@test.com",
//                BigDecimal.valueOf(1000),
//                3
//        );
//        roleEntity = new RoleEntity(3, "CUSTOMER", "customer");
//    }
//
//    @Test
//    void mustFindValueById() {
//
//        when(roleRepository.findById(1)).thenReturn(Mono.just(roleEntity));
//        when(mapper.map("test", Object.class)).thenReturn("test");
//
//        Mono<Role> result = roleRepositoryAdapter.findByName("CUSTOMER");
//
//        StepVerifier.create(result)
//                .expectNextMatches(value -> value.equals("test"))
//                .verifyComplete();
//    }
//
//    @Test
//    void mustFindAllValues() {
//        when(roleRepository.findAll()).thenReturn(Flux.just("test"));
//        when(mapper.map("test", Object.class)).thenReturn("test");
//
//        Flux<Object> result = roleRepositoryAdapter.findAll();
//
//        StepVerifier.create(result)
//                .expectNextMatches(value -> value.equals("test"))
//                .verifyComplete();
//    }
//
//    @Test
//    void mustFindByExample() {
//        when(roleRepository.findAll(any(Example.class))).thenReturn(Flux.just("test"));
//        when(mapper.map("test", Object.class)).thenReturn("test");
//
//        Flux<Object> result = roleRepositoryAdapter.findByExample("test");
//
//        StepVerifier.create(result)
//                .expectNextMatches(value -> value.equals("test"))
//                .verifyComplete();
//    }
//
//    @Test
//    void mustSaveValue() {
//        when(roleRepository.save("test")).thenReturn(Mono.just("test"));
//        when(mapper.map("test", Object.class)).thenReturn("test");
//
//        Mono<Object> result = roleRepositoryAdapter.save("test");
//
//        StepVerifier.create(result)
//                .expectNextMatches(value -> value.equals("test"))
//                .verifyComplete();
//    }
}
