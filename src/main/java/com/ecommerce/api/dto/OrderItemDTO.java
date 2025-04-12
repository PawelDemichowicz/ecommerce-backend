package com.ecommerce.api.dto;

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

    Integer productId;
    String productName;
    String productDescription;
    BigDecimal productPrice;
    BigDecimal totalPrice;
    Integer quantity;
}
