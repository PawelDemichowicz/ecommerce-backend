package com.ecommerce.business.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Value
@Builder
public class OrderItem {
    Order order;
    Product product;
    Integer quantity;
    BigDecimal price;
}
