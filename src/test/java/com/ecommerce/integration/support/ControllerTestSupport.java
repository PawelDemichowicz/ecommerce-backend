package com.ecommerce.integration.support;

import com.ecommerce.security.auth.AuthenticationController;
import com.ecommerce.security.auth.dto.AuthenticateRequest;
import com.ecommerce.security.auth.dto.RegisterRequest;
import com.ecommerce.util.DtoFixtures;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface ControllerTestSupport {

    ObjectMapper getObjectMapper();

    RequestSpecification requestSpecificationNoAuthorization();

    default String registerAndAuthenticateUser() {
        RegisterRequest someUser = DtoFixtures.someRegisterRequest();
        requestSpecificationNoAuthorization()
                .body(new RegisterRequest(someUser.getUsername(), someUser.getEmail(), someUser.getPassword()))
                .post(AuthenticationController.API_AUTH + AuthenticationController.API_REGISTER)
                .then()
                .statusCode(HttpStatus.OK.value());
        return requestSpecificationNoAuthorization()
                .body(new AuthenticateRequest(someUser.getEmail(), someUser.getPassword()))
                .post(AuthenticationController.API_AUTH + AuthenticationController.API_AUTHENTICATE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .path("token");
    }

    default String authenticateTestMasterAdmin() {
        return requestSpecificationNoAuthorization()
                .body(new AuthenticateRequest("test.master@admin.com", "testadmin123"))
                .post(AuthenticationController.API_AUTH + AuthenticationController.API_AUTHENTICATE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .path("token");
    }
}
