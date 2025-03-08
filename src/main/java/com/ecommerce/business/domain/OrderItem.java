package com.ecommerce.business.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class OrderItem {

    Order order;
    Product product;
    Integer quantity;
    BigDecimal price;
}
