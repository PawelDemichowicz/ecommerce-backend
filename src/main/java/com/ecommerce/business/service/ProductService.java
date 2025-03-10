package com.ecommerce.business.service;

import com.ecommerce.business.domain.Product;
import com.ecommerce.business.exception.NotFoundException;
import com.ecommerce.database.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product getProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new NotFoundException("Could not find product by id: [%s]".formatted(productId));
        }
        return product.get();
    }

    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name).stream()
                .toList();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
