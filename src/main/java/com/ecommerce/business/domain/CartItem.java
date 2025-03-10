package com.ecommerce.business.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItem {

    private User user;
    private Product product;
    private Integer quantity;
}
