package com.crediya.autenticacion.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @NotBlank(message = "El correo electrónico es obligatorio")
        @Email(message = "El correo electrónico es invalido")
        @JsonProperty("correo_electronico")
        String correoElectronico,
        @NotBlank(message = "La contrasenia es obligatoria")
        @Size(min = 8, message = "La contrasenia debe tener al menos 8 caracteres")
        String contrasenia
) {
}
