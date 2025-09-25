package com.crediya.autenticacion.api.dtos;

import com.crediya.autenticacion.model.constants.AuthConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateUserDTO(
        @NotBlank(message = AuthConstants.VALIDATION_ID_NUMBER_REQUIRED)
        @JsonProperty("numero_identificacion")
        String numeroIdentificacion,
        @NotBlank(message = AuthConstants.VALIDATION_NAME_REQUIRED)
        String nombres,
        @NotBlank(message = AuthConstants.VALIDATION_LASTNAME_REQUIRED)
        String apellidos,
        @Past(message = AuthConstants.VALIDATION_BIRTH_DATE_PAST)
        @JsonProperty("fecha_nacimiento")
        LocalDate fechaNacimiento,
        String direccion,
        String telefono,
        @NotBlank(message = AuthConstants.VALIDATION_EMAIL_REQUIRED)
        @Email(message = AuthConstants.VALIDATION_EMAIL_FORMAT)
        @JsonProperty("correo_electronico")
        String correoElectronico,
        @NotNull(message = AuthConstants.VALIDATION_SALARY_REQUIRED)
        BigDecimal salario,
        @JsonProperty("id_rol")
        Integer idRol,
        @NotBlank(message = AuthConstants.VALIDATION_PASSWORD_REQUIRED)
        @Size(min = AuthConstants.PASSWORD_MIN_LENGTH, message = AuthConstants.VALIDATION_PASSWORD_MIN_LENGTH)
        @Size(max = AuthConstants.PASSWORD_MAX_LENGTH, message = AuthConstants.VALIDATION_PASSWORD_MAX_LENGTH)
        String contrasenia
) {
}
