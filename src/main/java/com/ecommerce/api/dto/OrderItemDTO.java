package com.ecommerce.api.dto;

import com.ecommerce.business.domain.Order;
import com.ecommerce.business.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    Order order;
    Product product;
    Integer quantity;
    BigDecimal price;
}
