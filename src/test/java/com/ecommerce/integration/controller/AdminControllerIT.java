package com.ecommerce.integration.controller;

import com.ecommerce.api.dto.ProductsDTO;
import com.ecommerce.api.dto.request.ProductRequestDTO;
import com.ecommerce.api.dto.request.ProductUpdateRequestDTO;
import com.ecommerce.api.dto.response.ProductResponseDTO;
import com.ecommerce.integration.configuration.RestAssuredIntegrationTestBase;
import com.ecommerce.integration.support.AdminControllerTestSupport;
import com.ecommerce.integration.support.ProductControllerTestSupport;
import com.ecommerce.util.DtoFixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AdminControllerIT
        extends RestAssuredIntegrationTestBase implements AdminControllerTestSupport, ProductControllerTestSupport {

    @Test
    void shouldAddProduct() {
        // given
        ProductRequestDTO requestProduct = DtoFixtures.someProductRequestDTO();

        // when
        ProductResponseDTO addedProduct = addProduct(requestProduct);

        // then
        assertThat(addedProduct).isNotNull();
        assertThat(addedProduct.getId()).isNotNull();
        assertThat(addedProduct.getName()).isEqualTo(requestProduct.getName());
    }

    @Test
    void shouldUpdateProduct() {
        // given
        ProductRequestDTO requestProduct = DtoFixtures.someProductRequestDTO();
        ProductUpdateRequestDTO updateRequest = DtoFixtures.someProductUpdateRequestDTO();

        ProductResponseDTO addedProduct = addProduct(requestProduct);

        // when
        ProductResponseDTO updatedProduct = updateProduct(addedProduct.getId(), updateRequest);

        // then
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getId()).isEqualTo(addedProduct.getId());
        assertThat(updatedProduct.getName()).isEqualTo(updateRequest.getName());
    }

    @Test
    void shouldDeleteProductSuccessfully() {
        // given
        ProductRequestDTO requestProduct = DtoFixtures.someProductRequestDTO();
        ProductResponseDTO addedProduct = addProduct(requestProduct);

        // when
        deleteProduct(addedProduct.getId());

        // then
        ProductsDTO foundProducts = getAllProducts();
        assertThat(foundProducts.getProducts()).noneMatch(p -> p.getId().equals(addedProduct.getId()));
    }
}
