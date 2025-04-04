package com.ecommerce.api.controller;

import com.ecommerce.api.dto.ProductRequestDTO;
import com.ecommerce.api.dto.ProductResponseDTO;
import com.ecommerce.api.dto.mapper.ProductMapper;
import com.ecommerce.business.domain.Product;
import com.ecommerce.business.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(AdminController.API_ADMIN)
public class AdminController {

    public static final String API_ADMIN = "/admin";
    public static final String API_ADMIN_PRODUCTS = "/products";
    public static final String API_ADMIN_PRODUCTS_ID = "/products/{productId}";

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping(value = API_ADMIN_PRODUCTS)
    public ProductResponseDTO addProduct(
            @RequestBody @Valid ProductRequestDTO productRequestDTO
    ) {
        Product product = productService.addProduct(productMapper.mapFromRequestDTO(productRequestDTO));
        return productMapper.mapToResponseDTO(product);
    }

    @PutMapping(value = API_ADMIN_PRODUCTS_ID)
    public ProductResponseDTO updateProduct(
            @PathVariable Integer productId,
            @RequestBody @Valid ProductRequestDTO productRequestDTO
    ) {
        Product productToUpdate = productMapper.mapFromRequestDTO(productRequestDTO);
        Product product = productService.updateProduct(productId, productToUpdate);
        return productMapper.mapToResponseDTO(product);
    }

    @DeleteMapping(value = API_ADMIN_PRODUCTS_ID)
    public void deleteProduct(
            @PathVariable Integer productId
    ) {
        productService.deleteProduct(productId);
    }
}
