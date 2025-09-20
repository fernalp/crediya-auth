package com.crediya.autenticacion.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateUserDTO(
        @NotBlank(message = "El numero de identificacion es obligatorio")
        @JsonProperty("numero_identificacion")
        String numeroIdentificacion,
        @NotBlank(message = "El nombre es obligatorio")
        String nombres,
        @NotBlank(message = "El apellido es obligatorio")
        String apellidos,
        @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
        @JsonProperty("fecha_nacimiento")
        LocalDate fechaNacimiento,
        String direccion,
        String telefono,
        @NotBlank(message = "El correo electronico es obligatorio")
        @JsonProperty("correo_electronico")
        String correoElectronico,
        @NotNull(message = "El salario es obligatorio")
        BigDecimal salario,
        @JsonProperty("id_rol")
        Integer idRol,
        @NotBlank(message = "La contrasenia es obligatoria")
        @Size(min = 8, message = "La contrasenia debe tener al menos 8 caracteres")
        String contrasenia
) {
}
