package com.ecommerce.integration.controller;

import com.ecommerce.api.dto.ProductsDTO;
import com.ecommerce.api.dto.response.ProductResponseDTO;
import com.ecommerce.integration.configuration.RestAssuredIntegrationTestBase;
import com.ecommerce.integration.support.ProductControllerTestSupport;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductControllerIT
        extends RestAssuredIntegrationTestBase implements ProductControllerTestSupport {

    @Override
    public RequestSpecification requestSpecification() {
        return getUnauthenticatedRequest();
    }

    @Test
    void shouldReturnProductById() {
        ProductResponseDTO result = getProductById(1);
        assertThat(result.getId()).isEqualTo(1);
    }

    @Test
    void shouldReturnAllProducts() {
        ProductsDTO result = getAllProducts();
        assertThat(result.getProducts()).isNotEmpty();
    }

    @Test
    void shouldSearchProductsByName() {
        ProductsDTO result = getProductsByName("watch");
        assertThat(result.getProducts()).anyMatch(p -> p.getName().toLowerCase().contains("watch"));
    }
}
