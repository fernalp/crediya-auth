package com.crediya.autenticacion.model.user.valueObjects;

import com.crediya.autenticacion.model.exceptions.ValidationException;

public class Email {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String ERROR_CODE = "EMAIL_INVALID";
    private static final String ERROR_MESSAGE = "El correo electrónico no es válido";
    private final String value;

    private Email(String value) {
        this.value = value;
    }

    private static void validateEmail(String value) {
        if(!value.matches(EMAIL_REGEX)) {
            throw new ValidationException(ERROR_CODE, ERROR_MESSAGE);
        }
    }

    public static Email create(String value) {
        validateEmail(value);
        return new Email(value.trim().toLowerCase());
    }

    public String toString() {
        return value;
    }

}
