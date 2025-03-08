package com.ecommerce.business.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Value
@Builder
public class Product {
    String name;
    String description;
    BigDecimal price;
    Integer stock;
}
