package com.crediya.autenticacion.api.mappers;

import com.crediya.autenticacion.api.dtos.CreateUserDTO;
import com.crediya.autenticacion.api.dtos.UserResponseDTO;
import com.crediya.autenticacion.model.role.Role;
import com.crediya.autenticacion.model.user.User;

public class UserMapper {

    public static User toUser(CreateUserDTO createUserDTO){
        return new User(
                null,
                createUserDTO.numeroIdentificacion(),
                createUserDTO.nombres(),
                createUserDTO.apellidos(),
                createUserDTO.fechaNacimiento(),
                createUserDTO.direccion(),
                createUserDTO.telefono(),
                createUserDTO.correoElectronico(),
                createUserDTO.salario(),
                Role.builder().id(createUserDTO.idRol()).build(),
                createUserDTO.contrasenia()
        );
    }

    public static UserResponseDTO toUserResponseDTO(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getIdNumber(),
                user.getFirstname(),
                user.getLastname(),
                user.getBirthdate(),
                user.getAddress(),
                user.getPhone(),
                user.getEmail(),
                user.getSalary(),
                user.getRole().getId()
        );
    }

}
