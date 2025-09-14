package com.crediya.autenticacion.usecase.finduserbyidnumber;

import com.crediya.autenticacion.model.role.Role;
import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.model.user.gateways.UserRepository;
import com.crediya.autenticacion.usecase.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserByIdNumberUseCaseTest {

    @InjectMocks
    private FindUserByIdNumberUseCase findUserByIdNumberUseCase;

    @Mock
    private UserRepository userRepository;

    private User fakeUser = User.builder()
            .idNumber("12345678")
            .firstname("Fernando")
            .lastname("Garcia")
            .email("fernando.garcia@gmail.com")
            .address("123 Main St")
            .phone("123456789")
            .birthdate(LocalDate.of(2000, 1, 1))
            .salary(new BigDecimal(1000))
            .role(Role.builder().id(1).name("USER").build())
            .build();

    @Test
    void shouldFindUserByIdNumberWhenUserExists() {
        when(userRepository.findByIdNumber(fakeUser.getIdNumber())).thenReturn(Mono.just(fakeUser));
        Mono<User> result = findUserByIdNumberUseCase.execute(fakeUser.getIdNumber());
        StepVerifier.create(result)
                .expectNext(fakeUser)
                .expectComplete()
                .verify();
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        when(userRepository.findByIdNumber(fakeUser.getIdNumber())).thenReturn(Mono.empty());
        Mono<User> result = findUserByIdNumberUseCase.execute(fakeUser.getIdNumber());
        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    assertEquals(NotFoundException.class, error.getClass());
                    assertEquals("USER_NOT_FOUND", ((NotFoundException) error).getCode());
                    assertTrue(error.toString().contains("USER_NOT_FOUND"));
                })
                .verify();

    }

}