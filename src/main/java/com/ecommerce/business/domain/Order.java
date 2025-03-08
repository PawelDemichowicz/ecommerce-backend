package com.ecommerce.business.domain;

import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.List;

@Value
@Builder
public class Order {

    User user;
    OffsetDateTime orderDate;
    List<OrderItem> orderItems;
}
