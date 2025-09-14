package com.crediya.autenticacion.usecase.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends IllegalArgumentException{

    private final String code = "USER_NOT_FOUND";

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return String.format("%s: %s", code, super.getMessage());
    }

}
