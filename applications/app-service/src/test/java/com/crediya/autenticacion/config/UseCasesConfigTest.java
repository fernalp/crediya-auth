package com.crediya.autenticacion.config;

import com.crediya.autenticacion.model.role.gateways.RoleRepository;
import com.crediya.autenticacion.model.user.gateways.UserRepository;
import com.crediya.autenticacion.usecase.createuser.CreateUserUseCase;
import com.crediya.autenticacion.usecase.finduserbyidnumber.FindUserByIdNumberUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UseCasesConfigTest {

    @Test
    void testUseCaseBeansExist() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class)) {
            String[] beanNames = context.getBeanDefinitionNames();

            boolean useCaseBeanFound = false;
            for (String beanName : beanNames) {
                if (beanName.endsWith("UseCase")) {
                    useCaseBeanFound = true;
                    break;
                }
            }

            assertTrue(useCaseBeanFound, "No beans ending with 'Use Case' were found");
        }
    }

    @Configuration
    @Import(UseCasesConfig.class)
    static class TestConfig {

        @MockitoBean
        private UserRepository userRepository;

        @MockitoBean
        private RoleRepository roleRepository;

        @Bean
        public CreateUserUseCase createUserUseCase() {
            return new CreateUserUseCase(userRepository, roleRepository);
        }

        @Bean
        public FindUserByIdNumberUseCase findUserByIdNumberUseCase() {
            return new FindUserByIdNumberUseCase(userRepository);
        }
    }
}