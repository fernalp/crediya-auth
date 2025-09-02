package com.crediya.autenticacion.model.user.valueObjects;

import com.crediya.autenticacion.model.exceptions.ValidationException;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Salary {
    
    private static final BigDecimal SALARY_MAX = BigDecimal.valueOf(15000000);
    private static final String ERROR_CODE = "SALARY_INVALID";
    private static final String ERROR_MESSAGE_LESS_THAN_ZERO = "El salario no puede ser negativo";
    private static final String ERROR_MESSAGE_GREATER_THAN_MAX = "El salario no puede ser mayor a " + SALARY_MAX;
    
    private final BigDecimal value;
    
    private Salary(BigDecimal value) {
        this.value = value;
    }

    private static void validateSalary(BigDecimal salary) {
        validateLessThanZero(salary);
        validateGreaterThanMax(salary);
    }

    private static void validateGreaterThanMax(BigDecimal salary) {
        if(salary.compareTo(SALARY_MAX) > 0) {
            throw new ValidationException(ERROR_CODE, ERROR_MESSAGE_GREATER_THAN_MAX);
        }
    }

    private static void validateLessThanZero(BigDecimal salary) {
        if(salary.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(ERROR_CODE, ERROR_MESSAGE_LESS_THAN_ZERO);
        }
    }

    public static Salary create(BigDecimal value) {
        validateSalary(value);
        return new Salary(value);
    }

}
