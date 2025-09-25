package com.crediya.autenticacion.model.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthConstants {

    public static final String DEFAULT_ROLE = "CUSTOMER";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String ADVISOR_ROLE = "ADVISOR";

    public static final String DEFAULT_TOKEN_TYPE = "Bearer";

    public static final String ERROR_CODE_CONFLICT_USER = "CONFLICT_USER";
    public static final String ERROR_CODE_VALIDATION = "VALIDATION_ERROR";
    public static final String ERROR_CODE_SALARY = "SALARY_INVALID";
    public static final String ERROR_CODE_EMAIL = "EMAIL_INVALID";
    public static final String ERROR_CODE_AGE = "AGE_INVALID";
    public static final String ERROR_CODE_FORBIDDEN = "FORBIDDEN";
    public static final String ERROR_CODE_UNAUTHORIZED = "UNAUTHORIZED";
    public static final String ERROR_CODE_UNEXPECTED = "UNEXPECTED_ERROR";

    public static final String ERROR_MESSAGE_ID_NUMBER_EXISTS = "El número de identificación ya existe";
    public static final String ERROR_MESSAGE_EMAIL_EXISTS = "El correo electrónico ya existe";
    public static final String ERROR_MESSAGE_USER_NOT_FOUND = "El usuario no se encuentra registrado";
    public static final String ERROR_MESSAGE_USER_OR_PASS_INCORRECT = "Usuario o contraseña incorrectos";
    public static final String ERROR_MESSAGE_EMAIL_INVALID = "El correo electrónico no es válido";
    public static final String ERROR_MESSAGE_SALARY_NEGATIVE = "El salario no puede ser negativo";
    public static final String ERROR_MESSAGE_SALARY_TOO_HIGH = "El salario no puede ser mayor a ";
    public static final String ERROR_MESSAGE_AGE_INVALID = "Debe ser mayor de edad para registrarse";
    public static final String ERROR_MESSAGE_FORBIDDEN = "Acceso denegado: no tienes permiso para acceder a este recurso";
    public static final String ERROR_MESSAGE_UNAUTHORIZED = "Acceso denegado: no tienes credenciales válidas, por favor inicia sesión!";
    public static final String ERROR_MESSAGE_VALIDATION = "No podemos procesar la solicitud, verifica los datos proporcionados";
    public static final String ERROR_MESSAGE_UNEXPECTED = "Ocurrió un error inesperado, por favor intenta de nuevo, si el problema persiste contacta al administrador";

    public static final BigDecimal SALARY_MAX = BigDecimal.valueOf(15000000);
    public static final BigDecimal SALARY_MIN = BigDecimal.ZERO;
    public static final int MINIMUM_AGE = 18;
    public static final int MAXIMUM_AGE = 80;

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 50;

    public static final int NAME_MAX_LENGTH = 100;
    public static final int ADDRESS_MAX_LENGTH = 255;
    public static final int PHONE_MAX_LENGTH = 20;
    public static final int ID_NUMBER_MAX_LENGTH = 20;

    public static final String VALIDATION_ID_NUMBER_REQUIRED = "El número de identificación es obligatorio";
    public static final String VALIDATION_NAME_REQUIRED = "El nombre es obligatorio";
    public static final String VALIDATION_LASTNAME_REQUIRED = "El apellido es obligatorio";
    public static final String VALIDATION_EMAIL_REQUIRED = "El correo electrónico es obligatorio";
    public static final String VALIDATION_PASSWORD_REQUIRED = "La contraseña es obligatoria";
    public static final String VALIDATION_BIRTH_DATE_PAST = "La fecha de nacimiento debe ser anterior a la fecha actual";
    public static final String VALIDATION_SALARY_REQUIRED = "El salario es obligatorio";
    public static final String VALIDATION_PASSWORD_MIN_LENGTH = "La contraseña debe tener al menos 8 caracteres";
    public static final String VALIDATION_PASSWORD_MAX_LENGTH = "La contraseña debe tener menos de 50 caracteres";
    public static final String VALIDATION_PASSWORD_PATTERN = "La contraseña debe contener al menos una mayúscula, una minúscula, un número y un carácter especial";
    public static final String VALIDATION_EMAIL_FORMAT = "El correo electrónico no tiene un formato válido";

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final int BEARER_PREFIX_LENGTH = 7;
    public static final String CLAIMS_ROLE = "role";
    public static final String AUTHORITY_PREFIX = "ROLE_";

    public static final String[] AUTH_WHITELIST = {
            "/api/doc/swagger-ui.html",
            "/api/doc/api-docs/**",
            "/api/doc/swagger-ui/**",
            "/docs",
            "/scalar/**"
    };
    public static final String API_VERSION = "v1";
    public static final String API_PATH = "/api/" + API_VERSION;
    public static final String API_PATH_USERS = API_PATH + "/usuarios";
    public static final String API_PATH_LOGIN = API_PATH + "/login";

    public static final String LOG_USER_CREATED_SUCCESS = "Usuario creado exitosamente: {}";
    public static final String LOG_USER_FOUND_SUCCESS = "Usuario encontrado exitosamente: {}";
    public static final String LOG_USER_LOGIN_SUCCESS = "Usuario logueado exitosamente";
    public static final String LOG_ERROR_OCCURRED = "Error occurred: {}";

}