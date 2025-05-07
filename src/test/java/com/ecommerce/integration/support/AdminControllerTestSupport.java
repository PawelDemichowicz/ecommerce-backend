package com.ecommerce.integration.support;

import com.ecommerce.api.controller.AdminController;
import com.ecommerce.api.dto.request.ProductRequestDTO;
import com.ecommerce.api.dto.request.ProductUpdateRequestDTO;
import com.ecommerce.api.dto.response.ProductResponseDTO;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface AdminControllerTestSupport {

    RequestSpecification requestSpecificationAdminAuthorization();

    default ProductResponseDTO addProduct(ProductRequestDTO productRequest) {

        return requestSpecificationAdminAuthorization()
                .body(productRequest)
                .post(AdminController.API_ADMIN + AdminController.API_ADMIN_PRODUCTS)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ProductResponseDTO.class);
    }

    default ProductResponseDTO updateProduct(Integer productId, ProductUpdateRequestDTO productUpdateRequest) {
        return requestSpecificationAdminAuthorization()
                .body(productUpdateRequest)
                .put(AdminController.API_ADMIN + "/products/" + productId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ProductResponseDTO.class);
    }

    default void deleteProduct(Integer productId) {
        requestSpecificationAdminAuthorization()
                .delete(AdminController.API_ADMIN + "/products/" + productId)
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
