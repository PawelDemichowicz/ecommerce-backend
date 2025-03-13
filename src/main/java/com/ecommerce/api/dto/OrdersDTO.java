package com.ecommerce.api.dto;

import com.ecommerce.business.domain.OrderItem;
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
public class OrdersDTO {

    List<OrderDTO> orders;
}
