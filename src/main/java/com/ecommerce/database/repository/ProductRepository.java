package com.ecommerce.database.repository;

import com.ecommerce.business.domain.Product;
import com.ecommerce.database.entity.ProductEntity;
import com.ecommerce.database.entity.mapper.ProductEntityMapper;
import com.ecommerce.database.repository.jpa.ProductJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductEntityMapper productEntityMapper;

    public Optional<Product> findById(Integer productId) {
        return productJpaRepository.findById(productId)
                .map(productEntityMapper::mapFromEntity);
    }

    public List<Product> findByName(String name) {
        return productJpaRepository.findByNameContainingIgnoreCase(name).stream()
                .map(productEntityMapper::mapFromEntity)
                .toList();
    }

    public List<Product> findAll() {
        return productJpaRepository.findAll().stream()
                .map(productEntityMapper::mapFromEntity)
                .toList();
    }

    public Product saveProduct(Product product) {
        ProductEntity productToSave = productEntityMapper.mapToEntity(product);
        return productEntityMapper.mapFromEntity(productJpaRepository.save(productToSave));
    }

    public void updateProduct(Integer productId, Product product) {
        ProductEntity productToSave = productEntityMapper.mapToEntity(product);
        productToSave.setId(productId);
        productJpaRepository.save(productToSave);
    }

    public void deleteById(Integer productId) {
        productJpaRepository.deleteById(productId);
    }
}
