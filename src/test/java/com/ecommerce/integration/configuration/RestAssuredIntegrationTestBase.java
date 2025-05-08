package com.ecommerce.integration.configuration;

import com.ecommerce.integration.support.ControllerTestSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

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

    @BeforeEach
    protected void setupRestAssured() {
        userJWT = registerAndAuthenticateUser();
        adminJWT = authenticateTestMasterAdmin();
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
