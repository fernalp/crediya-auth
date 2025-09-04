package com.crediya.autenticacion.model.exceptions;

import lombok.Getter;

@Getter
public class ValidationException extends IllegalArgumentException{

    private final String code;

    public ValidationException(String code, String message) {
        super(message);
        this.code = code;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", code, super.getMessage());
    }

}
