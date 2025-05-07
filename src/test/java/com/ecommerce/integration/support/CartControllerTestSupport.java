package com.ecommerce.integration.support;

import com.ecommerce.api.controller.CartController;
import com.ecommerce.api.dto.CartItemsDTO;
import com.ecommerce.api.dto.request.CartItemRequestDTO;
import com.ecommerce.api.dto.response.CartItemResponseDTO;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface CartControllerTestSupport {

    RequestSpecification requestSpecificationUserAuthorization();

    default CartItemsDTO getAllCartItems() {
        return requestSpecificationUserAuthorization()
                .get(CartController.API_CART)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CartItemsDTO.class);
    }

    default CartItemResponseDTO addItemToCart(Integer productId, Integer quantity) {
        CartItemRequestDTO request = CartItemRequestDTO.builder()
                .productId(productId)
                .quantity(quantity)
                .build();

        return requestSpecificationUserAuthorization()
                .body(request)
                .post(CartController.API_CART)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(CartItemResponseDTO.class);
    }

    default void removeItemFromCart(Integer cartItemId) {
        requestSpecificationUserAuthorization()
                .delete(CartController.API_CART + "/" + cartItemId)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    default void removeAllItemsFromCart() {
        requestSpecificationUserAuthorization()
                .delete(CartController.API_CART + CartController.API_CART_CLEAR)
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
