package com.crediya.autenticacion.r2dbc.mappers;

import com.crediya.autenticacion.model.role.Role;
import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.r2dbc.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityMapperTest {

    private final BigInteger id = BigInteger.ONE;
    private final String idNumber = "123456789";
    private final String firstName = "jon";
    private final String lastName = "snow";
    private final LocalDate birthDate = LocalDate.of(2000, 1, 1);
    private final String address = "123 main st";
    private final String phone = "123-456-7890";
    private final String email = "jon.snow@example.com";
    private final BigDecimal salary = BigDecimal.valueOf(1000.0);
    private final Integer idRole = 1;

    private UserEntity fakeUserEntity;
    private User fakeUser;

    @BeforeEach
    void setUp(){
        fakeUserEntity = new UserEntity(
                id,
                idNumber,
                firstName,
                lastName,
                birthDate,
                address,
                phone,
                email,
                salary,
                idRole
        );
        fakeUser = new User(
                id,
                idNumber,
                firstName,
                lastName,
                birthDate,
                address,
                phone,
                email,
                salary,
                Role.builder().id(idRole).build()
        );
    }

    @Test
    void shouldUserWhenMapperEntity(){
        User user = UserEntityMapper.toUser(fakeUserEntity);
        assertEquals(id, user.getId());
        assertEquals(idNumber, user.getIdNumber());
        assertEquals(firstName, user.getFirstname());
        assertEquals(lastName, user.getLastname());
        assertEquals(birthDate, user.getBirthdate());
        assertEquals(address, user.getAddress());
        assertEquals(phone, user.getPhone());
        assertEquals(email, user.getEmail());
        assertEquals(salary, user.getSalary());
        assertEquals(idRole, user.getRole().getId());
    }

    @Test
    void shouldUserEntityWhenMapperUser(){
        UserEntity userEntity = UserEntityMapper.toUserEntity(fakeUser);
        assertEquals(id, userEntity.getId());
        assertEquals(idNumber, userEntity.getIdNumber());
        assertEquals(firstName, userEntity.getFirstName());
        assertEquals(lastName, userEntity.getLastName());
        assertEquals(birthDate, userEntity.getBirthDate());
        assertEquals(address, userEntity.getAddress());
        assertEquals(phone, userEntity.getPhone());
        assertEquals(email, userEntity.getEmail());
        assertEquals(salary, userEntity.getSalary());
        assertEquals(idRole, userEntity.getIdRole());
    }

}