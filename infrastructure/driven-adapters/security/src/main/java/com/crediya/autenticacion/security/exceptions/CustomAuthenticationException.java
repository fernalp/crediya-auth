package com.crediya.autenticacion.security.exceptions;

import lombok.Getter;

@Getter
public class CustomAuthenticationException extends IllegalArgumentException {

    private final String code = "AUTHENTICATION_ERROR";

    public CustomAuthenticationException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return String.format("%s: %s", code, getMessage());
    }
}
