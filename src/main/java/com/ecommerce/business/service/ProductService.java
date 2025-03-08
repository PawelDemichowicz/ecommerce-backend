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

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name).stream()
                .toList();
    }

    public Product getProductById(Integer id) {
        Optional<Product> productById = productRepository.findById(id);
        if (productById.isEmpty()) {
            throw new NotFoundException("Could not find product by id: [%s]".formatted(id));
        }
        return productById.get();
    }
}
