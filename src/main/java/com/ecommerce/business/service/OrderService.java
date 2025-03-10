package com.ecommerce.business.service;

import com.ecommerce.business.domain.CartItem;
import com.ecommerce.business.domain.Order;
import com.ecommerce.business.domain.OrderItem;
import com.ecommerce.business.domain.User;
import com.ecommerce.business.exception.NotFoundException;
import com.ecommerce.business.exception.ProcessingException;
import com.ecommerce.database.entity.enums.OrderStatus;
import com.ecommerce.database.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;
    private final CartService cartService;

    @Transactional
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Could not find order by id: [%s]".formatted(orderId)));
    }

    @Transactional
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUser(userId);
    }

    @Transactional
    public Order placeOrder(Long userId) {
        User user = userService.getUserById(userId);
        List<CartItem> cartItems = cartService.getCartItemsByUser(userId);

        if (cartItems.isEmpty()) {
            throw new ProcessingException("Cannot place order with an empty cart");
        }

        Order order = buildOrder(user);
        List<OrderItem> orderItems = buildOrderItems(cartItems, order);

        Order newOrder = order.withOrderItems(orderItems);
        orderRepository.saveOrder(newOrder);

        return newOrder;
    }

    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = getOrder(orderId);

        if (!order.getStatus().equals(OrderStatus.PENDING)) {
            throw new ProcessingException("Only pending orders can be cancelled");
        }

        Order updatedOrder = order.withStatus(OrderStatus.CANCELLED);
        orderRepository.saveOrder(updatedOrder);

        return updatedOrder;
    }

    private List<OrderItem> buildOrderItems(List<CartItem> cartItems, Order order) {
        return cartItems.stream()
                .map(cartItem -> OrderItem.builder()
                        .order(order)
                        .product(cartItem.getProduct())
                        .quantity(cartItem.getQuantity())
                        .price(cartItem.getProduct().getPrice())
                        .build())
                .toList();
    }

    private Order buildOrder(User user) {
        return Order.builder()
                .user(user)
                .orderDate(OffsetDateTime.from(LocalDateTime.now()))
                .status(OrderStatus.PENDING)
                .build();
    }
}
