package com.ecommerce.integration.support;

import com.ecommerce.api.controller.OrderController;
import com.ecommerce.api.dto.OrderDTO;
import com.ecommerce.api.dto.OrdersDTO;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface OrderControllerTestSupport {

    RequestSpecification requestSpecificationUserAuthorization();

    default OrderDTO getOrderById(Integer orderId) {
        return requestSpecificationUserAuthorization()
                .get(OrderController.API_ORDERS + "/" + orderId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(OrderDTO.class);
    }

    default OrdersDTO getOrdersByUser() {
        return requestSpecificationUserAuthorization()
                .get(OrderController.API_ORDERS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(OrdersDTO.class);
    }

    default OrderDTO placeOrder() {
        return requestSpecificationUserAuthorization()
                .post(OrderController.API_ORDERS)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(OrderDTO.class);
    }

    default OrderDTO cancelOrder(Integer orderId) {
        return requestSpecificationUserAuthorization()
                .patch(OrderController.API_ORDERS + "/" + orderId + "/cancel")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(OrderDTO.class);
    }
}
