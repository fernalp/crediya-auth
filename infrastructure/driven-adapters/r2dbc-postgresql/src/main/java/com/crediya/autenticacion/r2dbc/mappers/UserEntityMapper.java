package com.crediya.autenticacion.r2dbc.mappers;

import com.crediya.autenticacion.model.role.Role;
import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.r2dbc.entities.UserEntity;

public class UserEntityMapper {

    public static UserEntity toUserEntity(User user) {
        return new UserEntity(
                user.getId(),
                user.getIdNumber() == null ? null : user.getIdNumber().trim().toUpperCase(),
                user.getFirstname() == null ? null : user.getFirstname().trim().toLowerCase(),
                user.getLastname() == null ? null : user.getLastname().trim().toLowerCase(),
                user.getBirthdate(),
                user.getAddress() == null ? null : user.getAddress().trim().toLowerCase(),
                user.getPhone() == null ? null : user.getPhone().trim().toLowerCase(),
                user.getEmail() == null ? null : user.getEmail().trim().toLowerCase(),
                user.getSalary(),
                user.getRole() == null ? null : user.getRole().getId(),
                user.getPassword()
        );
    }

    public static User toUser(UserEntity userEntity) {
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
                Role.builder().id(userEntity.getIdRole()).build(),
                userEntity.getPassword()
        );
    }
}
