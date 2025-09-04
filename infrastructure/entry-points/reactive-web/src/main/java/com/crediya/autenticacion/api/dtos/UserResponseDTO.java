package com.crediya.autenticacion.api.dtos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

public record UserResponseDTO(
        BigInteger id,
        String numeroIdentificacion,
        String nombres,
        String apellidos,
        LocalDate fechaNacimiento,
        String direccion,
        String telefono,
        String correoElectronico,
        BigDecimal salario,
        Integer idRol
) {
}
