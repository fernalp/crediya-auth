package com.crediya.autenticacion.usecase.exceptions;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class NotFoundExceptionTest {

    @Test
    void testNotFoundException() {
        String CODE = "USER_NOT_FOUND";
        String MESSAGE = "User not found";

        StepVerifier.create(Mono.error(new NotFoundException(MESSAGE)))
                .expectErrorSatisfies(error -> {
                    assertInstanceOf(NotFoundException.class, error);
                    assertEquals(CODE, ((NotFoundException) error).getCode());
                    assertEquals(MESSAGE, error.getMessage());
                    assertTrue(error.toString().contains(MESSAGE));
                    assertTrue(error.toString().contains(CODE));
                })
                .verify();
    }

}