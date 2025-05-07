package com.ecommerce.util;

import com.ecommerce.api.dto.request.ProductRequestDTO;
import com.ecommerce.api.dto.request.ProductUpdateRequestDTO;
import com.ecommerce.security.auth.dto.RegisterRequest;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.UUID;

@UtilityClass
public class DtoFixtures {

    public static RegisterRequest someRegisterRequest() {
        UUID uuid = UUID.randomUUID();
        return RegisterRequest.builder()
                .username("user_" + uuid)
                .email("user" + uuid + "@example.com")
                .password("password123")
                .build();
    }

    public static ProductRequestDTO someProductRequestDTO() {
        String uniqueName = "Test Product " + UUID.randomUUID();
        return ProductRequestDTO.builder()
                .name(uniqueName)
                .description("Product description")
                .price(BigDecimal.valueOf(299.99))
                .stock(25)
                .build();
    }

    public static ProductUpdateRequestDTO someProductUpdateRequestDTO() {
        String uniqueName = "Updated Test Product " + UUID.randomUUID();
        return ProductUpdateRequestDTO.builder()
                .name(uniqueName)
                .description("Updated product description")
                .price(BigDecimal.valueOf(99.99))
                .stock(50)
                .build();
    }
}
