package com.ecommerce.business.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class Product {

    String name;
    String description;
    BigDecimal price;
    Integer stock;
}
