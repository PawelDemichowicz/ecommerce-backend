package com.ecommerce.api.dto;

import com.ecommerce.database.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    Integer id;
    UserDTO user;
    OffsetDateTime orderDate;
    OrderStatus status;
    List<OrderItemDTO> orderItems;
}
