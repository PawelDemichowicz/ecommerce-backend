package com.ecommerce.integration.controller;

import com.ecommerce.api.dto.OrderDTO;
import com.ecommerce.api.dto.OrdersDTO;
import com.ecommerce.database.entity.enums.OrderStatus;
import com.ecommerce.integration.configuration.RestAssuredIntegrationTestBase;
import com.ecommerce.integration.support.OrderControllerTestSupport;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderControllerIT
        extends RestAssuredIntegrationTestBase implements OrderControllerTestSupport {

    @Override
    public RequestSpecification requestSpecification() {
        return getAuthenticatedRequest();
    }

    @Test
    void shouldReturnProductById() {
        // given & when
        OrderDTO result = getOrderById(1);

        // then
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getUser().getEmail()).isEqualTo("john.doe@example.com");
        assertThat(result.getStatus()).isEqualTo(OrderStatus.PENDING);
    }

    @Test
    void shouldReturnOrdersForUser() {
        // when
        OrdersDTO result = getOrdersByUser();

        // then
        assertThat(result.getOrders()).hasSize(1);
        assertThat(result.getOrders()).allMatch(order -> order.getUser().getId() == 1);
    }

    @Test
    void shouldPlaceNewOrder() {
        // when
        OrderDTO result = placeOrder();

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getUser().getId()).isEqualTo(1);
        assertThat(result.getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(result.getOrderItems()).hasSize(2);
    }

    @Test
    void shouldCancelOrder() {
        // when
        OrderDTO result = cancelOrder(1);

        // then
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getStatus()).isEqualTo(OrderStatus.CANCELLED);
    }

}
