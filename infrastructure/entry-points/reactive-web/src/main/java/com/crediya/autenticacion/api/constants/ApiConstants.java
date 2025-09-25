package com.crediya.autenticacion.api.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiConstants {

    public static final String HTTP_STATUS_CODE_OK = String.valueOf(HttpStatus.OK.value());
    public static final String HTTP_STATUS_CODE_CREATED = String.valueOf(HttpStatus.CREATED.value());
    public static final String HTTP_STATUS_CODE_BAD_REQUEST = String.valueOf(HttpStatus.BAD_REQUEST.value());
    public static final String HTTP_STATUS_CODE_UNAUTHORIZED = String.valueOf(HttpStatus.UNAUTHORIZED.value());
    public static final String HTTP_STATUS_CODE_CONFLICT = String.valueOf(HttpStatus.CONFLICT.value());
    public static final String HTTP_STATUS_CODE_FORBIDDEN = String.valueOf(HttpStatus.FORBIDDEN.value());
    public static final String HTTP_STATUS_CODE_NOT_FOUND = String.valueOf(HttpStatus.NOT_FOUND.value());
    public static final String HTTP_STATUS_CODE_INTERNAL_SERVER_ERROR = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());

    public static final String SUCCESS_CREATED = "Usuario creado exitosamente";
    public static final String SUCCESS_FOUND = "Usuario encontrado exitosamente";
    public static final String LOGIN_SUCCESS = "Inicio de sesión exitoso";
    public static final String BAD_REQUEST = HttpStatus.BAD_REQUEST.getReasonPhrase();
    public static final String CONFLICT = HttpStatus.CONFLICT.getReasonPhrase();
    public static final String NOT_FOUND = HttpStatus.NOT_FOUND.getReasonPhrase();
    public static final String FORBIDDEN = HttpStatus.FORBIDDEN.getReasonPhrase();
    public static final String UNAUTHORIZED = HttpStatus.UNAUTHORIZED.getReasonPhrase();
    public static final String INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
}
