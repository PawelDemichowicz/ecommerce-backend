package com.ecommerce.database.repository;

import com.ecommerce.business.domain.Product;
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

    public List<Product> findAll() {
        return productJpaRepository.findAll().stream()
                .map(productEntityMapper::map)
                .toList();
    }

    public List<Product> findByName(String name) {
        return productJpaRepository.findByName().stream()
                .map(productEntityMapper::map)
                .toList();
    }

    public Optional<Product> findById(Integer id) {
        return productJpaRepository.findById()
                .map(productEntityMapper::map);
    }
}
