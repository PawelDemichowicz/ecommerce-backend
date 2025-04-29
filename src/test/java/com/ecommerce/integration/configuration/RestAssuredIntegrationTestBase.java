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

    protected RequestSpecification requestSpecification;

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @BeforeEach
    void setupRestAssured() {
        requestSpecification = RestAssured.given()
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
