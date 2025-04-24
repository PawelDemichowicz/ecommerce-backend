package com.ecommerce.business.service;

import com.ecommerce.business.domain.*;
import com.ecommerce.business.exception.NotFoundException;
import com.ecommerce.business.exception.ProcessingException;
import com.ecommerce.database.entity.enums.OrderStatus;
import com.ecommerce.database.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @Mock
    private CartService cartService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldReturnOrderById() {
        // given
        int orderId = 1;
        Order order = Order.builder().id(orderId).build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // when
        Order result = orderService.getOrder(orderId);

        // then
        assertEquals(orderId, result.getId());
    }

    @Test
    void shouldThrowWhenOrderNotFound() {
        // given
        int orderId = 1;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // when & then
        Throwable exception = assertThrows(NotFoundException.class,
                () -> orderService.getOrder(orderId));
        assertEquals("Could not find order by id: [%s]".formatted(orderId), exception.getMessage());
    }

    @Test
    void shouldReturnOrderByOrderIdAndUserId() {
        // given
        int userId = 5;
        int orderId = 1;
        User user = User.builder().id(userId).build();
        Order order = Order.builder().id(orderId).user(user).build();

        when(orderRepository.findByIdAndUserId(orderId, userId)).thenReturn(Optional.of(order));

        // when
        Order result = orderService.getOrderByOrderIdAndUserId(userId, orderId);

        // then
        assertEquals(orderId, result.getId());
    }

    @Test
    void shouldThrowWhenOrderByOrderIdAndUserIdNotFound() {
        // given
        int userId = 5;
        int orderId = 1;
        when(orderRepository.findByIdAndUserId(orderId, userId)).thenReturn(Optional.empty());

        // when & then
        Throwable exception = assertThrows(NotFoundException.class,
                () -> orderService.getOrderByOrderIdAndUserId(userId, orderId));
        assertEquals("Could not find order by id: [%s]".formatted(orderId), exception.getMessage());
    }

    @Test
    void shouldReturnOrdersByUser() {
        // given
        int userId = 2;
        List<Order> orders = List.of(Order.builder().id(1).build(), Order.builder().id(2).build());
        when(orderRepository.findByUser(userId)).thenReturn(orders);

        // when
        List<Order> result = orderService.getOrdersByUser(userId);

        // then
        assertEquals(2, result.size());
    }

    @Test
    void shouldPlaceOrder() {
        // given
        int userId = 1;
        User user = User.builder().id(userId).build();

        CartItem cartItem = CartItem.builder()
                .quantity(2)
                .product(Product.builder()
                        .id(10)
                        .name("Test")
                        .description("Test description")
                        .price(BigDecimal.TEN)
                        .build())
                .build();
        OrderItem orderItem = OrderItem.builder()
                .productId(10)
                .productName("Test")
                .productDescription("Test description")
                .productPrice(BigDecimal.TEN)
                .totalPrice(BigDecimal.valueOf(20))
                .quantity(2)
                .build();

        Order savedOrder = Order.builder()
                .user(user)
                .orderDate(any())
                .status(OrderStatus.PENDING)
                .orderItems(List.of(orderItem))
                .build();

        when(userService.getUserById(userId)).thenReturn(user);
        when(cartService.getCartItemsByUser(userId)).thenReturn(List.of(cartItem));
        when(orderRepository.issueOrder(any(Order.class))).thenReturn(savedOrder);

        // when
        Order result = orderService.placeOrder(userId);

        // then
        assertEquals(OrderStatus.PENDING, result.getStatus());
        assertEquals(1, result.getOrderItems().size());
        verify(productService).decreaseStock(10, 2);
        verify(cartService).clearCart(userId);
    }

    @Test
    void shouldThrowWhenCartIsEmpty() {
        // given
        int userId = 1;
        User user = User.builder().id(userId).build();
        when(userService.getUserById(userId)).thenReturn(user);
        when(cartService.getCartItemsByUser(userId)).thenReturn(List.of());

        // when & then
        Throwable exception = assertThrows(ProcessingException.class,
                () -> orderService.placeOrder(userId));
        assertEquals("Cannot place order with an empty cart", exception.getMessage());
    }

    @Test
    void shouldCancelPendingOrder() {
        // given
        int userId = 1;
        int orderId = 10;

        OrderItem orderItem = OrderItem.builder()
                .productId(10)
                .quantity(3)
                .build();

        Order order = Order.builder()
                .status(OrderStatus.PENDING)
                .orderItems(List.of(orderItem))
                .build();

        when(orderRepository.findByIdAndUserId(orderId, userId)).thenReturn(Optional.of(order));

        // when
        Order result = orderService.cancelOrder(userId, orderId);

        // then
        assertEquals(OrderStatus.CANCELLED, result.getStatus());
        verify(productService).increaseStock(10, 3);
        verify(orderRepository).saveOrder(result);
    }

    @Test
    void shouldThrowExceptionIfOrderIsNotPending() {
        // given
        int userId = 1;
        int orderId = 10;

        Order order = Order.builder()
                .status(OrderStatus.CANCELLED)
                .build();

        when(orderRepository.findByIdAndUserId(orderId, userId)).thenReturn(Optional.of(order));

        // when & then
        Throwable exception = assertThrows(ProcessingException.class,
                () -> orderService.cancelOrder(userId, orderId));
        assertEquals("Only pending orders can be cancelled", exception.getMessage());
        verify(orderRepository, never()).saveOrder(any());
    }
}