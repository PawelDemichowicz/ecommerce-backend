package com.ecommerce.util;

import com.ecommerce.business.domain.Order;
import com.ecommerce.business.domain.OrderItem;
import com.ecommerce.business.domain.User;
import com.ecommerce.database.entity.enums.OrderStatus;
import com.ecommerce.database.entity.enums.Role;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@UtilityClass
public class DomainFixtures {

    public static User someUser1() {
        return User.builder()
                .username("alice_wonder")
                .email("alice@example.com")
                .password("aliceSecurePass456")
                .role(Role.ADMIN)
                .build();
    }

    public static User someUser2() {
        return User.builder()
                .username("charlie_brown")
                .email("charlie@example.com")
                .password("charlieBrownPass")
                .role(Role.USER)
                .build();
    }

    public static OrderItem someOrderItem1() {
        return OrderItem.builder()
                .productId(1)
                .productName("Product 1")
                .productDescription("Description 1")
                .productPrice(new BigDecimal("50.00"))
                .totalPrice(new BigDecimal("150.00"))
                .quantity(3)
                .build();
    }

    public static OrderItem someOrderItem2() {
        return OrderItem.builder()
                .productId(2)
                .productName("Product 2")
                .productDescription("Description 2")
                .productPrice(new BigDecimal("100.00"))
                .totalPrice(new BigDecimal("200.00"))
                .quantity(2)
                .build();
    }

    public static Order someOrder(User user, List<OrderItem> orderItem) {
        return Order.builder()
                .user(user)
                .orderDate(OffsetDateTime.now())
                .status(OrderStatus.PENDING)
                .orderItems(orderItem)
                .build();
    }
}