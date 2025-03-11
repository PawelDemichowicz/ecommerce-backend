package com.ecommerce.business.service;

import com.ecommerce.business.domain.Product;
import com.ecommerce.business.exception.NotFoundException;
import com.ecommerce.database.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Could not find product by id: [%s]".formatted(productId)));
    }

    @Transactional
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name).stream()
                .toList();
    }

    @Transactional
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product addProduct(Product product) {
        productRepository.saveProduct(product);
        return product;
    }

    @Transactional
    public Product updateProduct(Long productId, Product product) {
        Product existingProduct = getProductById(productId);
        Product newProduct = buildProduct(existingProduct, product);
        productRepository.updateProduct(productId, newProduct);
        return newProduct;
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    private Product buildProduct(Product existingProduct, Product newProduct) {
        return Product.builder()
                .name(newProduct.getName() != null ? newProduct.getName() : existingProduct.getName())
                .description(newProduct.getDescription() != null
                        ? newProduct.getDescription() : existingProduct.getDescription())
                .price(newProduct.getPrice() != null ? newProduct.getPrice() : existingProduct.getPrice())
                .stock(newProduct.getStock() != null ? newProduct.getStock() : existingProduct.getStock())
                .build();
    }

}
