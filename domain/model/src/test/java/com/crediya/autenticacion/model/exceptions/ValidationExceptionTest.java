package com.crediya.autenticacion.model.exceptions;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class ValidationExceptionTest {

    @Test
    public void shouldThrowValidationException() {
        String CODE = "code";
        String MESSAGE = "message";
        StepVerifier.create(Mono.error(new ValidationException(CODE, MESSAGE)))
                .expectErrorSatisfies(error -> {
                    assertInstanceOf(ValidationException.class, error);
                    assertEquals(CODE, ((ValidationException) error).getCode());
                    assertEquals(MESSAGE, error.getMessage());
                    assertTrue(error.toString().contains(MESSAGE));
                    assertTrue(error.toString().contains(CODE));
                }).verify();
    }

}