package com.ecommerce.integration.controller;

import com.ecommerce.api.dto.CartItemsDTO;
import com.ecommerce.api.dto.request.ProductRequestDTO;
import com.ecommerce.api.dto.response.ProductResponseDTO;
import com.ecommerce.integration.configuration.RestAssuredIntegrationTestBase;
import com.ecommerce.integration.support.AdminControllerTestSupport;
import com.ecommerce.integration.support.CartControllerTestSupport;
import com.ecommerce.util.DtoFixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CartControllerIT
        extends RestAssuredIntegrationTestBase implements CartControllerTestSupport, AdminControllerTestSupport {

    @Test
    void shouldAddItemsToCart() {
        // given
        ProductRequestDTO requestProduct = DtoFixtures.someProductRequestDTO();
        ProductRequestDTO requestProduct2 = DtoFixtures.someProductRequestDTO();
        ProductResponseDTO addedProduct = addProduct(requestProduct);
        ProductResponseDTO addedProduct2 = addProduct(requestProduct2);

        // when
        addItemToCart(addedProduct.getId(), 2);
        addItemToCart(addedProduct2.getId(), 10);

        // then
        CartItemsDTO foundCartItems = getAllCartItems();
        assertThat(foundCartItems.getCartItems()).hasSize(2);
    }

    @Test
    void shouldRemoveItemFromCart() {
        // given
        ProductRequestDTO requestProduct = DtoFixtures.someProductRequestDTO();
        ProductRequestDTO requestProduct2 = DtoFixtures.someProductRequestDTO();
        ProductResponseDTO addedProduct = addProduct(requestProduct);
        ProductResponseDTO addedProduct2 = addProduct(requestProduct2);
        addItemToCart(addedProduct.getId(), 2);
        addItemToCart(addedProduct2.getId(), 10);

        // when
        removeItemFromCart(addedProduct2.getId());

        // then
        CartItemsDTO foundCartItems = getAllCartItems();
        assertThat(foundCartItems.getCartItems()).hasSize(1);
    }

    @Test
    void shouldRemoveAllItemsFromCart() {
        // given
        ProductRequestDTO requestProduct = DtoFixtures.someProductRequestDTO();
        ProductRequestDTO requestProduct2 = DtoFixtures.someProductRequestDTO();
        ProductResponseDTO addedProduct = addProduct(requestProduct);
        ProductResponseDTO addedProduct2 = addProduct(requestProduct2);
        addItemToCart(addedProduct.getId(), 2);
        addItemToCart(addedProduct2.getId(), 10);

        // when
        removeAllItemsFromCart();

        // then
        CartItemsDTO foundCartItems = getAllCartItems();
        assertThat(foundCartItems.getCartItems()).isEmpty();
    }
}
