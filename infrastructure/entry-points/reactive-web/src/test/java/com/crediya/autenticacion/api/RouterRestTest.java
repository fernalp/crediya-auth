package com.crediya.autenticacion.api;

import com.crediya.autenticacion.api.dtos.CreateUserDTO;
import com.crediya.autenticacion.api.dtos.UserResponseDTO;
import com.crediya.autenticacion.api.exceptions.GlobalErrorAttributes;
import com.crediya.autenticacion.api.exceptions.GlobalExceptionHandler;
import com.crediya.autenticacion.api.validator.ReactiveValidator;
import com.crediya.autenticacion.model.exceptions.ValidationException;
import com.crediya.autenticacion.model.role.Role;
import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.usecase.createuser.CreateUserUseCase;
import com.crediya.autenticacion.usecase.exceptions.ConflictException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = RouterRest.class)
@ContextConfiguration(classes = {
        RouterRest.class,
        HandlerV1.class,
        GlobalExceptionHandler.class,
        GlobalErrorAttributes.class
})
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private CreateUserUseCase createUserUseCase;

    @MockitoBean
    private ReactiveValidator reactiveValidator;

    private final String PATH = "/api/v1/usuarios";

    private CreateUserDTO fakeCreateUserDTO;
    private UserResponseDTO fakeUserDTO;
    private User fakeUser;

    @BeforeEach
    void setUp() {
        fakeCreateUserDTO = new CreateUserDTO(
                "123",
                "Fernando",
                "Almanza",
                LocalDate.of(2000, 1, 1),
                "address",
                "3213213213",
                "email@test.com",
                BigDecimal.valueOf(1000),
                3
        );
        
        fakeUser = User.builder()
                .id(BigInteger.ONE)
                .idNumber(fakeCreateUserDTO.numeroIdentificacion())
                .firstname(fakeCreateUserDTO.nombres())
                .lastname(fakeCreateUserDTO.apellidos())
                .birthdate(fakeCreateUserDTO.fechaNacimiento())
                .address(fakeCreateUserDTO.direccion())
                .phone(fakeCreateUserDTO.telefono())
                .email(fakeCreateUserDTO.correoElectronico())
                .salary(fakeCreateUserDTO.salario())
                .role(Role.builder().id(fakeCreateUserDTO.idRol()).build())
                .build();

        fakeUserDTO = new UserResponseDTO(
                fakeUser.getId(),
                fakeUser.getIdNumber(),
                fakeUser.getFirstname(),
                fakeUser.getLastname(),
                fakeUser.getBirthdate(),
                fakeUser.getAddress(),
                fakeUser.getPhone(),
                fakeUser.getEmail(),
                fakeUser.getSalary(),
                fakeUser.getRole().getId()
        );
    }

//    @Test
//    void testListenGETUseCaseV1() {
//        webTestClient.get()
//                .uri("/api/v1/usecase/path")
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(String.class)
//                .value(userResponse -> {
//                            Assertions.assertThat(userResponse).isEmpty();
//                        }
//                );
//    }

//    @Test
//    void testListenGETOtherUseCaseV1() {
//        webTestClient.get()
//                .uri("/api/v1/otherusercase/path")
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(String.class)
//                .value(userResponse -> {
//                            Assertions.assertThat(userResponse).isEmpty();
//                        }
//                );
//    }

    @Test
    void shouldCreateUserAndReturnUserDTO() {
        when(reactiveValidator.validate(any(CreateUserDTO.class))).thenReturn(Mono.just(fakeCreateUserDTO));
        when(createUserUseCase.execute(any(User.class))).thenReturn(Mono.just(fakeUser));

        webTestClient.post()
                .uri(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(fakeCreateUserDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(fakeUserDTO.id().intValue())
                .jsonPath("$.numeroIdentificacion").isEqualTo(fakeUserDTO.numeroIdentificacion())
                .jsonPath("$.nombres").isEqualTo(fakeUserDTO.nombres())
                .jsonPath("$.apellidos").isEqualTo(fakeUserDTO.apellidos())
                .jsonPath("$.correoElectronico").isEqualTo(fakeUserDTO.correoElectronico());
    }

    @Test
    void shouldCreateUserAndReturnThrowableWhenValidatorFails() {
        String message = "Error";
        String code = "VALIDATION_ERROR";
        when(reactiveValidator.validate(any(CreateUserDTO.class))).thenReturn(Mono.error(new ValidationException(code, message)));
        when(createUserUseCase.execute(any(User.class))).thenReturn(Mono.just(fakeUser));

        webTestClient.post()
                .uri(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(fakeCreateUserDTO)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.code").isEqualTo(code)
                .jsonPath("$.message").isEqualTo(message);
    }

    @Test
    void shouldCreateUserAndReturnThrowableWhenEmailOrIdNumberAlreadyExists() {
        String message = "Error";
        String code = "CONFLICT_ERROR";
        when(reactiveValidator.validate(any(CreateUserDTO.class))).thenReturn(Mono.just(fakeCreateUserDTO));
        when(createUserUseCase.execute(any(User.class))).thenReturn(Mono.error(new ConflictException(code, message)));
        webTestClient.post()
                .uri(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(fakeCreateUserDTO)
                .exchange()
                .expectStatus().value(status -> assertEquals(status, HttpStatus.CONFLICT.value()))
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.code").isEqualTo(code)
                .jsonPath("$.message").isEqualTo(message);
    }

    @Test
    void shouldCreateUserAndReturnThrowableWhenEmailOrSalaryIsInvalid() {
        String message = "Error";
        String code = "VALIDATION_ERROR";
        when(reactiveValidator.validate(any(CreateUserDTO.class))).thenReturn(Mono.just(fakeCreateUserDTO));
        when(createUserUseCase.execute(any(User.class))).thenReturn(Mono.error(new ValidationException(code, message)));
        webTestClient.post()
                .uri(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(fakeCreateUserDTO)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.code").isEqualTo(code)
                .jsonPath("$.message").isEqualTo(message);
    }
}
