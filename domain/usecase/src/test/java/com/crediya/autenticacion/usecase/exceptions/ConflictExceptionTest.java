package com.crediya.autenticacion.usecase.exceptions;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class ConflictExceptionTest {

    @Test
    void shouldThrowConflictException () {
        String CODE = "CONFLICT_USER";
        String MESSAGE = "User already exists";

        StepVerifier.create(Mono.error(new ConflictException(CODE, MESSAGE)))
                .expectErrorSatisfies(error -> {
                    assertInstanceOf(ConflictException.class, error);
                    assertEquals(CODE, ((ConflictException) error).getCode());
                    assertEquals(MESSAGE, error.getMessage());
                    assertTrue(error.toString().contains(MESSAGE));
                    assertTrue(error.toString().contains(CODE));
                })
                .verify();
    }

}