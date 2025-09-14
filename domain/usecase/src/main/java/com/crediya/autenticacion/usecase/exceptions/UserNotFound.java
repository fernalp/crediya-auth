package com.crediya.autenticacion.usecase.exceptions;

import lombok.Getter;

@Getter
public class UserNotFound extends IllegalArgumentException{

    private final String code = "USER_NOT_FOUND";

    public UserNotFound(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return String.format("%s: %s", code, super.getMessage());
    }

}
