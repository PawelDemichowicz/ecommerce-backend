package com.ecommerce.database.repository.jpa;

import com.ecommerce.database.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Integer> {

    List<ProductEntity> findByName(String name);
}
