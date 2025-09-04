package com.crediya.autenticacion.usecase.exceptions;

import lombok.Getter;

@Getter
public class ConflictException extends IllegalStateException {
    private String code;
    public ConflictException(String code, String message) {
        super(message);
        this.code = code;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", code, super.getMessage());
    }
}
