package com.ecommerce.integration.controller;

import com.ecommerce.integration.configuration.RestAssuredIntegrationTestBase;
import com.ecommerce.integration.support.AuthenticationControllerTestSupport;
import com.ecommerce.security.auth.dto.AuthenticateRequest;
import com.ecommerce.security.auth.dto.AuthenticationResponse;
import com.ecommerce.security.auth.dto.RegisterRequest;
import com.ecommerce.util.DtoFixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticationControllerIT
        extends RestAssuredIntegrationTestBase implements AuthenticationControllerTestSupport {

    @Test
    void shouldRegisterUser() {
        // given
        RegisterRequest registerRequest = DtoFixtures.someRegisterRequest();

        // when
        AuthenticationResponse response = register(registerRequest);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isNotBlank();
    }

    @Test
    void shouldAuthenticateUser() {
        // given
        RegisterRequest registerRequest = DtoFixtures.someRegisterRequest();
        register(registerRequest);

        AuthenticateRequest loginRequest = AuthenticateRequest.builder()
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .build();

        // when
        AuthenticationResponse response = authenticate(loginRequest);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isNotBlank();
    }
}
