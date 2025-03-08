package com.ecommerce.database.repository.jpa;

import com.ecommerce.database.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemJpaRepository extends JpaRepository<CartItemEntity, Integer> {
}
