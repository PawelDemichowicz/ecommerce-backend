package com.ecommerce.business.domain;

import com.ecommerce.database.entity.enums.OrderStatus;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.OffsetDateTime;
import java.util.List;

@With
@Value
@Builder(toBuilder = true)
public class Order {

    Integer id;
    User user;
    OffsetDateTime orderDate;
    OrderStatus status;
    List<OrderItem> orderItems;
}
