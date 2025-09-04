package com.crediya.autenticacion.usecase.createuser;

import com.crediya.autenticacion.model.role.Role;
import com.crediya.autenticacion.model.role.gateways.RoleRepository;
import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.model.user.gateways.UserRepository;
import com.crediya.autenticacion.usecase.exceptions.ConflictException;
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
class CreateUserUseCaseTest {

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    private User fakeUser;

    private Role fakeRole;

    @BeforeEach
    void setUp() {

        fakeRole = Role.builder()
                .id(1)
                .name("CUSTOMER")
                .description("Customer")
                .build();

        fakeUser = User.builder()
                .id(BigInteger.ONE)
                .firstname("Jon")
                .lastname("Doe")
                .birthdate(LocalDate.of(2000, 1, 1))
                .address("123 Main St")
                .phone("123456789")
                .email("jon.doe@gmail.com")
                .idNumber("123456789")
                .salary(BigDecimal.valueOf(1000))
                .role(fakeRole)
                .build();
    }

    @Test
    void shouldCreateUserWhenUserIsCorrect () {

        when(roleRepository.findByName(anyString())).thenReturn(Mono.just(fakeRole));
        when(userRepository.existsByEmail(anyString())).thenReturn(Mono.just(false));
        when(userRepository.existsByIdNumber(anyString())).thenReturn(Mono.just(false));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(fakeUser));

        Mono<User> result = createUserUseCase.execute(fakeUser);

        StepVerifier.create(result)
                .expectNext(fakeUser)
                .expectComplete()
                .verify();

    }

    @Test
    void shouldThrowExceptionWhenEmailIsAlreadyRegistered () {

        when(roleRepository.findByName(anyString())).thenReturn(Mono.just(fakeRole));
        when(userRepository.existsByEmail(anyString())).thenReturn(Mono.just(true));

        StepVerifier.create(createUserUseCase.execute(fakeUser))
                .expectErrorSatisfies(error -> {
                    assertEquals(ConflictException.class, error.getClass());
                    assertEquals("CONFLICT_USER", ((ConflictException) error).getCode());
                    assertTrue(error.toString().contains("CONFLICT_USER"));
                })
                .verify();
    }

    @Test
    void shouldThrowExceptionWhenIdNumberIsAlreadyRegistered () {
        when(roleRepository.findByName(anyString())).thenReturn(Mono.just(fakeRole));
        when(userRepository.existsByEmail(anyString())).thenReturn(Mono.just(false));
        when(userRepository.existsByIdNumber(anyString())).thenReturn(Mono.just(true));

        StepVerifier.create(createUserUseCase.execute(fakeUser))
                .expectErrorSatisfies(error -> {
                    assertEquals(ConflictException.class, error.getClass());
                    assertEquals("CONFLICT_USER", ((ConflictException) error).getCode());
                    assertTrue(error.toString().contains("CONFLICT_USER"));
                })
                .verify();

    }

}