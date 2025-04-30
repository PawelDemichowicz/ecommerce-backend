package com.ecommerce.integration.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.specification.RequestSpecification;

public interface ControllerTestSupport {

    ObjectMapper getObjectMapper();

    RequestSpecification getAuthenticatedRequest();

    RequestSpecification getUnauthenticatedRequest();
}
