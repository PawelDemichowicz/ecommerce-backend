package com.ecommerce.api.controller;

import com.ecommerce.api.dto.OrderDTO;
import com.ecommerce.api.dto.OrdersDTO;
import com.ecommerce.api.dto.mapper.OrderMapper;
import com.ecommerce.business.domain.Order;
import com.ecommerce.business.service.OrderService;
import com.ecommerce.security.util.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(OrderController.API_ORDERS)
public class OrderController {

    public static final String API_ORDERS = "/orders";
    public static final String API_ORDER_ID = "/{orderId}";
    public static final String API_ORDER_ID_CANCEL = "/{orderId}/cancel";

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping(value = API_ORDER_ID)
    public OrderDTO getOrder(
            @PathVariable Integer orderId
    ) {
        return orderMapper.mapToDTO(orderService.getOrder(orderId));
    }

    @GetMapping
    public ResponseEntity<OrdersDTO> getOrdersByUser(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Integer userId = userDetails.getUserId();
        OrdersDTO orders = OrdersDTO.builder()
                .orders(orderService.getOrdersByUser(userId).stream()
                        .map(orderMapper::mapToDTO)
                        .toList())
                .build();
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public OrderDTO placeOrder(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Integer userId = userDetails.getUserId();
        Order order = orderService.placeOrder(userId);
        return orderMapper.mapToDTO(order);
    }

    @PatchMapping(value = API_ORDER_ID_CANCEL)
    public OrderDTO cancelOrder(
            @PathVariable Integer orderId
    ) {
        Order order = orderService.cancelOrder(orderId);
        return orderMapper.mapToDTO(order);
    }
}
