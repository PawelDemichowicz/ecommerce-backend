package com.ecommerce.api.controller;

import com.ecommerce.api.dto.ProductDTO;
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
    public static final String API_ADMIN_ORDERS = "/orders";

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping(value = API_ADMIN_PRODUCTS)
    public ProductDTO addProduct(
            @RequestBody @Valid ProductDTO productDTO
    ) {
        Product product = productService.addProduct(productMapper.mapFromDTO(productDTO));
        return productMapper.mapToDTO(product);
    }

    @PutMapping(value = API_ADMIN_PRODUCTS_ID)
    public ProductDTO updateProduct(
            @PathVariable Integer productId,
            @RequestBody @Valid ProductDTO productDTO
    ) {
        Product productToUpdate = productMapper.mapFromDTO(productDTO);
        Product product = productService.updateProduct(productId, productToUpdate);
        return productMapper.mapToDTO(product);
    }

    @DeleteMapping(value = API_ADMIN_PRODUCTS_ID)
    public void deleteProduct(
            @PathVariable Integer productId
    ) {
        productService.deleteProduct(productId);
    }
}
