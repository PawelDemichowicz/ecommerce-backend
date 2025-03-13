package com.ecommerce.business.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;

@With
@Value
@Builder
public class Product {

    Integer id;
    String name;
    String description;
    BigDecimal price;
    Integer stock;
}
