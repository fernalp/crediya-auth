package com.crediya.autenticacion.api.config;

import com.crediya.autenticacion.api.HandlerV1;
import com.crediya.autenticacion.api.RouterRest;
import com.crediya.autenticacion.api.dtos.CreateUserDTO;
import com.crediya.autenticacion.api.exceptions.GlobalErrorAttributes;
import com.crediya.autenticacion.api.exceptions.GlobalExceptionHandler;
import com.crediya.autenticacion.api.validator.ReactiveValidator;
import com.crediya.autenticacion.model.role.Role;
import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.usecase.createuser.CreateUserUseCase;
import com.crediya.autenticacion.usecase.finduserbyidnumber.FindUserByIdNumberUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest()
@ContextConfiguration(classes = {
        RouterRest.class,
        HandlerV1.class,
        GlobalExceptionHandler.class,
        GlobalErrorAttributes.class
})
@Import({CorsConfig.class, SecurityHeadersConfig.class})
class SecurityHeadersConfigTest {

    private static final String PATH = "/api/v1/users";

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private CreateUserUseCase createUserUseCase;

    @MockitoBean
    private FindUserByIdNumberUseCase findUserByIdNumberUseCase;

    @MockitoBean
    private ReactiveValidator reactiveValidator;

    @MockitoBean
    private TransactionalOperator tx;

    private final User fakeUser = User.builder()
            .id(BigInteger.ONE)
            .idNumber("123456789")
            .birthdate(LocalDate.parse("2000-01-01"))
            .phone("123456789")
            .address("address")
            .email("email@test.com")
            .firstname("Fernando")
            .lastname("Almanza")
            .salary(new BigDecimal(1000))
            .role(Role.builder().id(3).name("CUSTOMER").description("Customer").build())
            .build();

    private final CreateUserDTO fakeCreateUserDTO = new CreateUserDTO(
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
    @BeforeEach
    void setUp() {
        when(createUserUseCase.execute(any(User.class))).thenReturn(Mono.just(fakeUser));
        when(reactiveValidator.validate(any(CreateUserDTO.class))).thenReturn(Mono.just(fakeCreateUserDTO));
        when(tx.transactional(any(Mono.class))).thenReturn(Mono.just(fakeUser));
    }

    @Test
    void testSecurityHeadersConfig() {
        webTestClient.post()
                .uri(PATH)
                .bodyValue(fakeCreateUserDTO)
                .exchange()
                .expectHeader().valueEquals("Content-Security-Policy", "default-src 'self'; frame-ancestors 'self'; form-action 'self'")
                .expectHeader().valueEquals("Strict-Transport-Security", "max-age=31536000;")
                .expectHeader().valueEquals("X-Content-Type-Options", "nosniff")
                .expectHeader().valueEquals("Server", "")
                .expectHeader().valueEquals("Cache-Control", "no-store")
                .expectHeader().valueEquals("Pragma", "no-cache");
    }

}