package com.ecommerce.api.dto;

import com.ecommerce.business.domain.Product;
import com.ecommerce.business.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

    User user;
    Product product;
    Integer quantity;
}
