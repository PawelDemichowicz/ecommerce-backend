package com.ecommerce.business.service;

import com.ecommerce.business.domain.Product;
import com.ecommerce.business.exception.NotFoundException;
import com.ecommerce.business.exception.ProcessingException;
import com.ecommerce.database.repository.CartItemRepository;
import com.ecommerce.database.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public Product getProductById(Integer productId) {
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
        return productRepository.saveProduct(product);
    }

    @Transactional
    public Product updateProduct(Integer productId, Product product) {
        Product existingProduct = getProductById(productId);
        Product updatedProduct = buildProduct(existingProduct, product);
        return productRepository.updateProduct(productId, updatedProduct);
    }

    @Transactional
    public void deleteProduct(Integer productId) {
        cartItemRepository.deleteByProductId(productId);
        productRepository.deleteById(productId);
    }

    @Transactional
    public void decreaseStock(Integer productId, Integer quantity) {
        Product product = getProductById(productId);
        validateStock(product, quantity);

        Product updatedProduct = product.withStock(product.getStock() - quantity);
        updateProduct(productId, updatedProduct);
    }

    @Transactional
    public void increaseStock(Integer productId, Integer quantity) {
        Product product = getProductById(productId);
        Product updatedProduct = product.withStock(product.getStock() + quantity);
        updateProduct(productId, updatedProduct);
    }

    public void validateStock(Product product, Integer requestedQuantity) {
        if (product.getStock() < requestedQuantity) {
            throw new ProcessingException("Not enough stock for product with id: [%s]".formatted(product.getId()));
        }
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
