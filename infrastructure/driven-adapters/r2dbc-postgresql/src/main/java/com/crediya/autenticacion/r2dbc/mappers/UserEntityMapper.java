package com.crediya.autenticacion.r2dbc.mappers;

import com.crediya.autenticacion.model.role.Role;
import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.r2dbc.entities.UserEntity;
import reactor.core.publisher.Mono;

public class UserEntityMapper {

    public static UserEntity toUserEntity(User user) {
        return new UserEntity(
                user.getId(),
                user.getIdNumber(),
                user.getFirstname(),
                user.getLastname(),
                user.getBirthdate(),
                user.getAddress(),
                user.getPhone(),
                user.getEmail().toString(),
                user.getSalary().getValue(),
                user.getRole().getId()
        );
    }

    public static User toUser(UserEntity userEntity) {
        Role role = new Role();
        role.setId(userEntity.getIdRole());
        return new User(
                userEntity.getId(),
                userEntity.getIdNumber(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getBirthDate(),
                userEntity.getAddress(),
                userEntity.getPhone(),
                userEntity.getEmail(),
                userEntity.getSalary(),
                role
        );
    }
}
