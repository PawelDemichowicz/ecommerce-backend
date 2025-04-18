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

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;
    private final CartService cartService;
    private final ProductService productService;

    @Transactional
    public Order getOrder(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Could not find order by id: [%s]".formatted(orderId)));
    }

    @Transactional
    public Order getOrderByOrderIdAndUserId(Integer userId, Integer orderId) {
        return orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new NotFoundException("Could not find order by id: [%s]".formatted(orderId)));
    }

    @Transactional
    public List<Order> getOrdersByUser(Integer userId) {
        return orderRepository.findByUser(userId);
    }

    @Transactional
    public Order placeOrder(Integer userId) {
        User user = userService.getUserById(userId);
        List<CartItem> cartItems = cartService.getCartItemsByUser(userId);

        if (cartItems.isEmpty()) {
            throw new ProcessingException("Cannot place order with an empty cart");
        }

        for (CartItem cartItem : cartItems) {
            productService.decreaseStock(cartItem.getProduct().getId(), cartItem.getQuantity());
        }

        List<OrderItem> orderItems = buildOrderItems(cartItems);
        Order order = buildOrder(user, orderItems);

        Order newOrder = orderRepository.issueOrder(order);
        cartService.clearCart(userId);
        return newOrder;
    }

    @Transactional
    public Order cancelOrder(Integer userId, Integer orderId) {
        Order order = getOrderByOrderIdAndUserId(userId, orderId);

        if (!order.getStatus().equals(OrderStatus.PENDING)) {
            throw new ProcessingException("Only pending orders can be cancelled");
        }

        for (OrderItem orderItem : order.getOrderItems()) {
            productService.increaseStock(orderItem.getProductId(), orderItem.getQuantity());
        }

        Order updatedOrder = order.withStatus(OrderStatus.CANCELLED);
        orderRepository.saveOrder(updatedOrder);

        return updatedOrder;
    }

    private List<OrderItem> buildOrderItems(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(cartItem -> OrderItem.builder()
                        .productId(cartItem.getProduct().getId())
                        .productName(cartItem.getProduct().getName())
                        .productDescription(cartItem.getProduct().getDescription())
                        .productPrice(cartItem.getProduct().getPrice())
                        .totalPrice(cartItem.getProduct().getPrice()
                                .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                        .quantity(cartItem.getQuantity())
                        .build())
                .toList();
    }

    private Order buildOrder(User user, List<OrderItem> orderItems) {
        return Order.builder()
                .user(user)
                .orderDate(OffsetDateTime.now())
                .status(OrderStatus.PENDING)
                .orderItems(orderItems)
                .build();
    }
}
