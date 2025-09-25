package com.crediya.autenticacion.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

public record UserResponseDTO(
        BigInteger id,
        @JsonProperty("numero_identificacion")
        String numeroIdentificacion,
        String nombres,
        String apellidos,
        @JsonProperty("fecha_nacimiento")
        LocalDate fechaNacimiento,
        String direccion,
        String telefono,
        @JsonProperty("correo_electronico")
        String correoElectronico,
        BigDecimal salario,
        @JsonProperty("id_rol")
        Integer idRol
) {
}
