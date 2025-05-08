package com.ecommerce.integration.controller;

import com.ecommerce.api.dto.CartItemsDTO;
import com.ecommerce.api.dto.OrderDTO;
import com.ecommerce.api.dto.OrdersDTO;
import com.ecommerce.api.dto.request.ProductRequestDTO;
import com.ecommerce.api.dto.response.ProductResponseDTO;
import com.ecommerce.database.entity.enums.OrderStatus;
import com.ecommerce.integration.configuration.RestAssuredIntegrationTestBase;
import com.ecommerce.integration.support.AdminControllerTestSupport;
import com.ecommerce.integration.support.CartControllerTestSupport;
import com.ecommerce.integration.support.OrderControllerTestSupport;
import com.ecommerce.util.DtoFixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderControllerIT
        extends RestAssuredIntegrationTestBase
        implements OrderControllerTestSupport, AdminControllerTestSupport, CartControllerTestSupport {

    @Test
    void shouldReturnOrderById() {
        // given
        ProductRequestDTO requestProduct = DtoFixtures.someProductRequestDTO();
        ProductResponseDTO addedProduct = addProduct(requestProduct);

        addItemToCart(addedProduct.getId(), 2);
        OrderDTO placedOrder = placeOrder();

        // when
        OrderDTO foundOrder = getOrderById(placedOrder.getId());

        // then
        assertThat(foundOrder.getId()).isEqualTo(placedOrder.getId());
        assertThat(foundOrder.getOrderItems().get(0).getProductId()).isEqualTo(addedProduct.getId());
        assertThat(foundOrder.getOrderItems().get(0).getQuantity()).isEqualTo(2);
        assertThat(foundOrder.getStatus()).isEqualTo(OrderStatus.PENDING);
    }

    @Test
    void shouldReturnOrdersForUser() {
        // given
        ProductRequestDTO requestProduct = DtoFixtures.someProductRequestDTO();
        ProductResponseDTO addedProduct = addProduct(requestProduct);
        addItemToCart(addedProduct.getId(), 2);
        placeOrder();

        ProductRequestDTO requestProduct2 = DtoFixtures.someProductRequestDTO();
        ProductResponseDTO addedProduct2 = addProduct(requestProduct2);
        addItemToCart(addedProduct2.getId(), 10);
        placeOrder();


        // when
        OrdersDTO foundOrderForUser = getOrdersByUser();

        // then
        assertThat(foundOrderForUser.getOrders()).hasSize(2);
        assertThat(foundOrderForUser.getOrders()).anyMatch(order -> order.getStatus().equals(OrderStatus.PENDING));
    }

    @Test
    void shouldPlaceNewOrder() {
        // given
        ProductRequestDTO requestProduct = DtoFixtures.someProductRequestDTO();
        ProductRequestDTO requestProduct2 = DtoFixtures.someProductRequestDTO();
        ProductResponseDTO addedProduct = addProduct(requestProduct);
        ProductResponseDTO addedProduct2 = addProduct(requestProduct2);

        addItemToCart(addedProduct.getId(), 2);
        addItemToCart(addedProduct2.getId(), 10);

        // when
        OrderDTO result = placeOrder();

        // then
        CartItemsDTO allCartItems = getAllCartItems();
        assertThat(allCartItems.getCartItems()).isEmpty();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(result.getOrderItems()).hasSize(2);
    }

    @Test
    void shouldCancelOrder() {
        // given
        ProductRequestDTO requestProduct = DtoFixtures.someProductRequestDTO();
        ProductResponseDTO addedProduct = addProduct(requestProduct);

        addItemToCart(addedProduct.getId(), 2);
        OrderDTO placedOrder = placeOrder();

        // when
        OrderDTO canceledOrder = cancelOrder(placedOrder.getId());

        // then
        assertThat(canceledOrder.getId()).isEqualTo(placedOrder.getId());
        assertThat(canceledOrder.getUser()).isEqualTo(placedOrder.getUser());
        assertThat(canceledOrder.getStatus()).isEqualTo(OrderStatus.CANCELLED);
    }
}
