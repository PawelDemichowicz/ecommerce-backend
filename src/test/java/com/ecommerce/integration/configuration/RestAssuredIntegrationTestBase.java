package com.ecommerce.integration.configuration;

import com.ecommerce.integration.support.ControllerTestSupport;
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

    private String userJWT;
    private String adminJWT;

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @BeforeAll
    protected void setupRestAssured() {
        userJWT = authenticateUser(
                "test.user@example.com",
                "password123"
        );
        adminJWT = authenticateUser(
                "test.admin@example.com",
                "password123"
        );
    }

    public RequestSpecification requestSpecificationNoAuthorization() {
        return restAssuredBase();
    }

    public RequestSpecification requestSpecificationUserAuthorization() {
        return restAssuredBase()
                .header("Authorization", "Bearer " + userJWT);
    }

    public RequestSpecification requestSpecificationAdminAuthorization() {
        return restAssuredBase()
                .header("Authorization", "Bearer " + adminJWT);
    }

    private RequestSpecification restAssuredBase() {
        return RestAssured
                .given()
                .config(getRestAssuredConfig())
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .basePath(basePath)
                .port(port);
    }

    private RestAssuredConfig getRestAssuredConfig() {
        return RestAssuredConfig
                .config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((type, s) -> objectMapper));
    }
}
