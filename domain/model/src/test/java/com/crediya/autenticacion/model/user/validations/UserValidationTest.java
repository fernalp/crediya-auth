package com.crediya.autenticacion.model.user.validations;

import com.crediya.autenticacion.model.exceptions.ValidationException;
import com.crediya.autenticacion.model.role.Role;
import com.crediya.autenticacion.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserValidationTest {

    private User userCorrect;

    @BeforeEach
    public void setUp() {
        userCorrect = User.builder()
                .idNumber("123456789")
                .firstname("John")
                .lastname("Doe")
                .birthdate(LocalDate.of(2000, 1, 1))
                .address("123 Main St")
                .phone("123456789")
                .email("john.doe@example.com")
                .salary(new BigDecimal("1000000"))
                .role(Role.builder().id(3).name("CUSTOMER").description("Customer").build())
                .build();
    }

    @Test
    public void shouldPassWhenUserIsCorrect() {
        StepVerifier.create(UserValidation.validate(userCorrect))
                .expectNext(userCorrect)
                .verifyComplete();
    }

    @Test
    public void shouldFailWhenEmailIsInvalid() {
        userCorrect.setEmail("invalid");
        StepVerifier.create(UserValidation.validate(userCorrect))
                .expectErrorSatisfies( error -> {
                    assertEquals("El correo electrónico no es válido", error.getMessage());
                    assertInstanceOf(ValidationException.class, error);
                    assertEquals("EMAIL_INVALID", ((ValidationException) error).getCode());
                })
                .verify();
    }

    @Test
    public void shouldFailWhenSalaryIsNegative() {
        userCorrect.setSalary(new BigDecimal("-1000000"));
        StepVerifier.create(UserValidation.validate(userCorrect))
                .expectErrorSatisfies( error -> {
                    assertInstanceOf(ValidationException.class, error);
                    assertEquals("El salario no puede ser negativo", error.getMessage());
                    assertEquals("SALARY_INVALID", ((ValidationException) error).getCode());
                })
                .verify();
    }

    @Test
    public void shouldFailWhenSalaryIsGreaterThanMax() {
        userCorrect.setSalary(new BigDecimal("15000001"));
        StepVerifier.create(UserValidation.validate(userCorrect))
                .expectErrorSatisfies( error -> {
                    assertInstanceOf(ValidationException.class, error);
                    assertEquals("El salario no puede ser mayor a 15000000", error.getMessage());
                    assertEquals("SALARY_INVALID", ((ValidationException) error).getCode());
                })
                .verify();
    }

}