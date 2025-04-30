package com.ecommerce.integration.configuration;

import com.ecommerce.integration.support.ControllerTestSupport;
import com.ecommerce.util.JwtTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class RestAssuredIntegrationTestBase
        extends AbstractIT implements ControllerTestSupport {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected JwtTestUtils jwtTestUtils;

    private RequestSpecification authenticatedRequestSpecification;
    private RequestSpecification unauthenticatedRequestSpecification;

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Override
    public RequestSpecification getAuthenticatedRequest() {
        return authenticatedRequestSpecification;
    }

    @Override
    public RequestSpecification getUnauthenticatedRequest() {
        return unauthenticatedRequestSpecification;
    }

    @BeforeAll
    void setupRestAssured() {
        String jwtToken = jwtTestUtils.generateTokenForUser(1, "john.doe@example.com");

        authenticatedRequestSpecification = RestAssured.given()
                .config(getRestAssuredConfig())
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .port(port)
                .basePath(basePath)
                .header("Authorization", "Bearer " + jwtToken);

        unauthenticatedRequestSpecification = RestAssured.given()
                .config(getRestAssuredConfig())
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .port(port)
                .basePath(basePath);
    }

    private RestAssuredConfig getRestAssuredConfig() {
        return RestAssuredConfig.config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((type, s) -> objectMapper));
    }
}
