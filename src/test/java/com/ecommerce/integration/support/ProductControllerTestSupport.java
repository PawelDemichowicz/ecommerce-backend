package com.ecommerce.integration.support;

import com.ecommerce.api.controller.ProductController;
import com.ecommerce.api.dto.ProductsDTO;
import com.ecommerce.api.dto.response.ProductResponseDTO;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface ProductControllerTestSupport {

    RequestSpecification requestSpecificationNoAuthorization();

    default ProductResponseDTO getProductById(Integer productId) {
        return requestSpecificationNoAuthorization()
                .get(ProductController.API_PRODUCTS + "/" + productId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ProductResponseDTO.class);
    }

    default ProductsDTO getProductsByName(String productName) {
        return requestSpecificationNoAuthorization()
                .queryParam("productName", productName)
                .get(ProductController.API_PRODUCTS + ProductController.API_PRODUCT_SEARCH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ProductsDTO.class);
    }

    default ProductsDTO getAllProducts() {
        return requestSpecificationNoAuthorization()
                .get(ProductController.API_PRODUCTS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ProductsDTO.class);
    }
}
