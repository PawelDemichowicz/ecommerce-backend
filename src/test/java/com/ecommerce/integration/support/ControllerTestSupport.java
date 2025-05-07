package com.ecommerce.integration.support;

import com.ecommerce.security.auth.AuthenticationController;
import com.ecommerce.security.auth.dto.AuthenticateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface ControllerTestSupport {

    ObjectMapper getObjectMapper();

    RequestSpecification requestSpecificationNoAuthorization();

    default String authenticateUser(String email, String password) {
        return requestSpecificationNoAuthorization()
                .body(new AuthenticateRequest(email, password))
                .post(AuthenticationController.API_AUTH + AuthenticationController.API_AUTHENTICATE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .path("token");
    }
}
