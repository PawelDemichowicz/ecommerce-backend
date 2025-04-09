package com.ecommerce.api.dto;

import com.ecommerce.api.dto.response.ProductResponseDTO;
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

    ProductResponseDTO product;
    Integer quantity;
    BigDecimal price;
}
