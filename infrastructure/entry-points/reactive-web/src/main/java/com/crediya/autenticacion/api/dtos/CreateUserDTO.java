package com.crediya.autenticacion.api.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateUserDTO(
//        @NotBlank(message = "El numero de identificacion es obligatorio")
        String numeroIdentificacion,
//        @NotBlank(message = "El nombre es obligatorio")
        String nombres,
//        @NotBlank(message = "El apellido es obligatorio")
        String apellidos,
//        @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
        LocalDate fechaNacimiento,
        String direccion,
        String telefono,
//        @NotBlank(message = "El correo electronico es obligatorio")
        String correoElectronico,
//        @NotBlank(message = "El salario es obligatorio")
        BigDecimal salario,
        Integer idRol
) {
}
