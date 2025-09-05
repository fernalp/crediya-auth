package com.crediya.autenticacion.r2dbc.repositories;

import com.crediya.autenticacion.model.role.Role;
import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.r2dbc.entities.RoleEntity;
import com.crediya.autenticacion.r2dbc.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserReactiveRepositoryAdapterTest {

    @InjectMocks
    UserReactiveRepositoryAdapter userRepositoryAdapter;

    @Mock
    UserReactiveRepository userRepository;

    UserEntity fakeUserEntity;
    RoleEntity fakeRoleEntity;

    @BeforeEach
    void setUp() {
        fakeUserEntity = new UserEntity(
                BigInteger.ONE,
                "123456789",
                "fernando",
                "almanza",
                LocalDate.parse("2020-01-01"),
                "address",
                "3213213211",
                "email@test.com",
                BigDecimal.valueOf(1000),
                3
        );
        fakeRoleEntity = new RoleEntity(3, "CUSTOMER", "customer");
    }

    @Test
    void shouldTrueWhenExistsByIdNumber() {
        when(userRepository.existsByIdNumber(anyString())).thenReturn(Mono.just(true));

        Mono<Boolean> result = userRepositoryAdapter.existsByIdNumber(fakeUserEntity.getIdNumber());

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void shouldTrueWhenExistsByEmail () {
        when(userRepository.existsByEmail(anyString())).thenReturn(Mono.just(true));
        Mono<Boolean> result = userRepositoryAdapter.existsByEmail(fakeUserEntity.getEmail());

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void shouldUserWhenFindByIdNumber() {

        when(userRepository.findByIdNumber(anyString())).thenReturn(Mono.just(fakeUserEntity));

        Mono<User> result = userRepositoryAdapter.findByIdNumber(fakeUserEntity.getIdNumber());

        StepVerifier.create(result)
                .expectNextMatches(user -> {
                    assertEquals(fakeUserEntity.getIdNumber(), user.getIdNumber());
                    assertEquals(fakeUserEntity.getFirstName(), user.getFirstname());
                    assertEquals(fakeUserEntity.getLastName(), user.getLastname());
                    assertEquals(fakeUserEntity.getBirthDate(), user.getBirthdate());
                    assertEquals(fakeUserEntity.getAddress(), user.getAddress());
                    assertEquals(fakeUserEntity.getPhone(), user.getPhone());
                    assertEquals(fakeUserEntity.getEmail(), user.getEmail());
                    assertEquals(fakeUserEntity.getSalary(), user.getSalary());
                    assertEquals(fakeUserEntity.getIdRole(), user.getRole().getId());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void shouldUserWhenFindValueByEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.just(fakeUserEntity));
        Mono<User> result = userRepositoryAdapter.findByEmail(fakeUserEntity.getEmail());

        StepVerifier.create(result)
                .expectNextMatches( user -> {
                    assertEquals(fakeUserEntity.getIdNumber(), user.getIdNumber());
                    assertEquals(fakeUserEntity.getFirstName(), user.getFirstname());
                    assertEquals(fakeUserEntity.getLastName(), user.getLastname());
                    assertEquals(fakeUserEntity.getBirthDate(), user.getBirthdate());
                    assertEquals(fakeUserEntity.getAddress(), user.getAddress());
                    assertEquals(fakeUserEntity.getPhone(), user.getPhone());
                    assertEquals(fakeUserEntity.getEmail(), user.getEmail());
                    assertEquals(fakeUserEntity.getSalary(), user.getSalary());
                    assertEquals(fakeUserEntity.getIdRole(), user.getRole().getId());
                    return true;
                } )
                .verifyComplete();
    }

    @Test
    void shouldUserWhenFindById() {
        when(userRepository.findById(any(BigInteger.class))).thenReturn(Mono.just(fakeUserEntity));
        Mono<User> result = userRepositoryAdapter.findById(fakeUserEntity.getId());
        StepVerifier.create(result)
                .expectNextMatches( user -> {
                    assertEquals(fakeUserEntity.getIdNumber(), user.getIdNumber());
                    assertEquals(fakeUserEntity.getFirstName(), user.getFirstname());
                    assertEquals(fakeUserEntity.getLastName(), user.getLastname());
                    assertEquals(fakeUserEntity.getBirthDate(), user.getBirthdate());
                    assertEquals(fakeUserEntity.getAddress(), user.getAddress());
                    assertEquals(fakeUserEntity.getPhone(), user.getPhone());
                    assertEquals(fakeUserEntity.getEmail(), user.getEmail());
                    assertEquals(fakeUserEntity.getSalary(), user.getSalary());
                    assertEquals(fakeUserEntity.getIdRole(), user.getRole().getId());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void shouldUserWhenSave() {
        User fakeUser = User.builder()
                        .id(fakeUserEntity.getId())
                        .idNumber(fakeUserEntity.getIdNumber())
                        .firstname(fakeUserEntity.getFirstName())
                        .lastname(fakeUserEntity.getLastName())
                        .birthdate(fakeUserEntity.getBirthDate())
                        .address(fakeUserEntity.getAddress())
                        .phone(fakeUserEntity.getPhone())
                        .email(fakeUserEntity.getEmail())
                        .salary(fakeUserEntity.getSalary())
                        .role(Role.builder().id(fakeUserEntity.getIdRole()).build())
                        .build();

        when(userRepository.save(any(UserEntity.class))).thenReturn(Mono.just(fakeUserEntity));
        Mono<User> result = userRepositoryAdapter.save(fakeUser);
        StepVerifier.create(result)
                .expectNextMatches( user -> {
                    assertEquals(fakeUserEntity.getIdNumber(), user.getIdNumber());
                    assertInstanceOf(User.class, user);
                    return true;
                })
                .verifyComplete();
    }
}