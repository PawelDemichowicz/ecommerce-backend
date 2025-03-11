package com.ecommerce.api.controller;

import com.ecommerce.api.dto.ProductDTO;
import com.ecommerce.api.dto.ProductsDTO;
import com.ecommerce.api.dto.mapper.ProductMapper;
import com.ecommerce.business.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping(ProductController.API_PRODUCTS)
public class ProductController {

    public static final String API_PRODUCTS = "/cars";
    public static final String API_PRODUCT_ID = "/{id}";

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping(API_PRODUCT_ID)
    public ResponseEntity<ProductDTO> getProduct(
            @PathVariable Long productId
    ) {
        if (Objects.isNull(productId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .ok(productMapper.mapToDTO(productService.getProductById(productId)));
    }

    @GetMapping
    public ProductsDTO getAllProducts() {
        return ProductsDTO.builder()
                .products(productService.getAllProducts().stream()
                        .map(productMapper::mapToDTO)
                        .toList())
                .build();
    }
}
