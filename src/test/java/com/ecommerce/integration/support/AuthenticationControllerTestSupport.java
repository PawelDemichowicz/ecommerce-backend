package com.ecommerce.integration.support;

import com.ecommerce.security.auth.AuthenticationController;
import com.ecommerce.security.auth.dto.AuthenticateRequest;
import com.ecommerce.security.auth.dto.AuthenticationResponse;
import com.ecommerce.security.auth.dto.RegisterRequest;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface AuthenticationControllerTestSupport {

    RequestSpecification requestSpecificationNoAuthorization();

    default AuthenticationResponse register(RegisterRequest request) {
        return requestSpecificationNoAuthorization()
                .body(request)
                .post(AuthenticationController.API_AUTH + AuthenticationController.API_REGISTER)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(AuthenticationResponse.class);
    }

    default AuthenticationResponse authenticate(AuthenticateRequest request) {
        return requestSpecificationNoAuthorization()
                .body(request)
                .post(AuthenticationController.API_AUTH + AuthenticationController.API_AUTHENTICATE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(AuthenticationResponse.class);
    }
}
