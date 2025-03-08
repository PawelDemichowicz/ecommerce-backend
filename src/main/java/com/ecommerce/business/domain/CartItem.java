package com.ecommerce.business.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CartItem {

    User user;
    Product product;
    Integer quantity;
}
