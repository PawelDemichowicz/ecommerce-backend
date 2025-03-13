package com.ecommerce.api.controller;

import com.ecommerce.api.dto.ProductDTO;
import com.ecommerce.api.dto.ProductsDTO;
import com.ecommerce.api.dto.mapper.ProductMapper;
import com.ecommerce.business.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping(ProductController.API_PRODUCTS)
public class ProductController {

    public static final String API_PRODUCTS = "/products";
    public static final String API_PRODUCT_ID = "/{productId}";
    public static final String API_PRODUCT_SEARCH = "/search";

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping(value = API_PRODUCT_ID)
    public ResponseEntity<ProductDTO> getProduct(
            @PathVariable Integer productId
    ) {
        if (Objects.isNull(productId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .ok(productMapper.mapToDTO(productService.getProductById(productId)));
    }

    @GetMapping(value = API_PRODUCT_SEARCH)
    public ProductsDTO getProductsByName(
            @RequestParam(value = "productName") String name
    ) {
        return ProductsDTO.builder()
                .products(productService.getProductsByName(name).stream()
                        .map(productMapper::mapToDTO)
                        .toList())
                .build();
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
