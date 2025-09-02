package com.crediya.autenticacion.usecase.exceptions;

import com.crediya.autenticacion.model.exceptions.ValidationException;

public class ConflictException extends ValidationException {
    public ConflictException(String code, String message) {
        super(code, message);
    }
}
