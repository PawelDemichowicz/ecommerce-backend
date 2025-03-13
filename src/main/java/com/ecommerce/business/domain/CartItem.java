package com.ecommerce.business.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class CartItem {

    Integer id;
    User user;
    Product product;
    Integer quantity;
}
