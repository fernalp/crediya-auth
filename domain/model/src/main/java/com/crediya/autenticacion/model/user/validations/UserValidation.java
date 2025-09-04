package com.crediya.autenticacion.model.user.validations;

import com.crediya.autenticacion.model.exceptions.ValidationException;
import com.crediya.autenticacion.model.user.User;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class UserValidation {

    private static final BigDecimal SALARY_MAX = BigDecimal.valueOf(15000000);
    private static final String ERROR_CODE_SALARY = "SALARY_INVALID";
    private static final String ERROR_MESSAGE_LESS_THAN_ZERO = "El salario no puede ser negativo";
    private static final String ERROR_MESSAGE_GREATER_THAN_MAX = "El salario no puede ser mayor a " + SALARY_MAX;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String ERROR_CODE = "EMAIL_INVALID";
    private static final String ERROR_MESSAGE = "El correo electrónico no es válido";

    public static Mono<User> validate(User user) {
        return Mono.when(
                validateEmail(user.getEmail()),
                validateSalary(user.getSalary())
        ).thenReturn(user);
    }

    private static Mono<Void> validateSalary(BigDecimal salary) {

        if(salary.compareTo(BigDecimal.ZERO) < 0) {
            return Mono.error(new ValidationException(ERROR_CODE_SALARY, ERROR_MESSAGE_LESS_THAN_ZERO));
        }
        if(salary.compareTo(SALARY_MAX) > 0) {
            return Mono.error(new ValidationException(ERROR_CODE_SALARY, ERROR_MESSAGE_GREATER_THAN_MAX));
        }
        return Mono.empty();

    }

    private static Mono<Void> validateEmail(String email) {
        if(!email.matches(EMAIL_REGEX)) {
            return Mono.error(new ValidationException(ERROR_CODE, ERROR_MESSAGE));
        }
        return Mono.empty();
    }

}
