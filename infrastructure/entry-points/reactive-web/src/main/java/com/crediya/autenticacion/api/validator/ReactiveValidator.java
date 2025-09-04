package com.crediya.autenticacion.api.validator;

import com.crediya.autenticacion.model.exceptions.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class ReactiveValidator {

    private final Validator validator;

    public <T> Mono<T> validate(T target) {
        Set<ConstraintViolation<T>> violations = validator.validate(target);
        String code = "VALIDATION_ERROR";
        String message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
        if (!violations.isEmpty()) {
            return Mono.error(new ValidationException(code, message));
        }
        return Mono.just(target);
    }
}
