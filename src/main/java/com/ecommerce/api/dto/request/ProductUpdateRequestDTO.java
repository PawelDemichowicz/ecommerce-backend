package com.ecommerce.api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequestDTO {

    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters long")
    String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    String description;

    @DecimalMin(value = "0.00", inclusive = false, message = "Invalid price. It must be bigger than 0")
    BigDecimal price;

    @Min(value = 0, message = "Stock quantity cannot be negative")
    Integer stock;
}
