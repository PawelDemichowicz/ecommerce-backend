package com.ecommerce.business.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class OrderItem {

    Integer id;
    Order order;
    Product product;
    Integer quantity;
    BigDecimal price;
}
