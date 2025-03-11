package com.ecommerce.business.service;

import com.ecommerce.business.domain.Order;
import com.ecommerce.database.entity.enums.OrderStatus;
import com.ecommerce.database.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminOrderService {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Transactional
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderService.getOrder(orderId);
        Order updatedOrder = order.withStatus(status);

        orderRepository.saveOrder(updatedOrder);
        return updatedOrder;
    }
}
