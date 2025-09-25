package com.crediya.autenticacion.model.user.validations;

import com.crediya.autenticacion.model.constants.AuthConstants;
import com.crediya.autenticacion.model.exceptions.ValidationException;
import com.crediya.autenticacion.model.user.User;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

public class UserValidation {


    public static Mono<User> validate(User user) {
        return Mono.when(
                validateEmail(user.getEmail()),
                validateSalary(user.getSalary()),
                validateAge(user.getBirthdate())
        ).thenReturn(user);
    }

    private static Mono<Void> validateSalary(BigDecimal salary) {
        if(salary.compareTo(AuthConstants.SALARY_MIN) < 0) {
            return Mono.error(new ValidationException(AuthConstants.ERROR_CODE_SALARY, AuthConstants.ERROR_MESSAGE_SALARY_NEGATIVE));
        }
        if(salary.compareTo(AuthConstants.SALARY_MAX) > 0) {
            return Mono.error(new ValidationException(AuthConstants.ERROR_CODE_SALARY, 
                    AuthConstants.ERROR_MESSAGE_SALARY_TOO_HIGH + AuthConstants.SALARY_MAX));
        }
        return Mono.empty();
    }

    private static Mono<Void> validateEmail(String email) {
        if(!email.matches(AuthConstants.EMAIL_REGEX)) {
            return Mono.error(new ValidationException(AuthConstants.ERROR_CODE_EMAIL, AuthConstants.ERROR_MESSAGE_EMAIL_INVALID));
        }
        return Mono.empty();
    }

    private static Mono<Void> validateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return Mono.empty();
        }
        
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        
        if (age < AuthConstants.MINIMUM_AGE) {
            return Mono.error(new ValidationException(AuthConstants.ERROR_CODE_AGE, AuthConstants.ERROR_MESSAGE_AGE_INVALID));
        }
        
        return Mono.empty();
    }

}
