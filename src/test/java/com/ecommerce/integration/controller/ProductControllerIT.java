package com.ecommerce.integration.controller;

import com.ecommerce.api.dto.ProductsDTO;
import com.ecommerce.api.dto.request.ProductRequestDTO;
import com.ecommerce.api.dto.response.ProductResponseDTO;
import com.ecommerce.integration.configuration.RestAssuredIntegrationTestBase;
import com.ecommerce.integration.support.AdminControllerTestSupport;
import com.ecommerce.integration.support.ProductControllerTestSupport;
import com.ecommerce.util.DtoFixtures;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class ProductControllerIT
        extends RestAssuredIntegrationTestBase implements ProductControllerTestSupport, AdminControllerTestSupport {

    @Test
    void shouldReturnProductById() {
        // given
        ProductRequestDTO requestProduct = DtoFixtures.someProductRequestDTO();
        ProductResponseDTO addedProduct = addProduct(requestProduct);

        // when
        ProductResponseDTO foundProduct = getProductById(addedProduct.getId());

        // then
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getId()).isNotNull();
        assertThat(foundProduct.getName()).isEqualTo(requestProduct.getName());
    }

    @Test
    void shouldReturnAllProducts() {
        // given
        addProduct(DtoFixtures.someProductRequestDTO());
        addProduct(DtoFixtures.someProductRequestDTO());

        // when
        ProductsDTO foundProducts = getAllProducts();

        // then
        assertThat(foundProducts.getProducts()).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void shouldSearchProductsByName() {
        // given
        ProductRequestDTO requestProduct = DtoFixtures.someProductRequestDTO();
        addProduct(requestProduct);

        // when
        ProductsDTO foundProducts = getProductsByName("test");

        // then
        assertThat(foundProducts.getProducts()).anyMatch(p -> p.getName().toLowerCase().contains("test"));
    }
}
