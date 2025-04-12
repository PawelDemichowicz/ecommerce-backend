package com.ecommerce.business.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class OrderItem {

    Integer id;
    Order order;
    Integer productId;
    String productName;
    String productDescription;
    BigDecimal productPrice;
    BigDecimal totalPrice;
    Integer quantity;
}
