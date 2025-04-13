package com.ecommerce.database.repository.jpa;

import com.ecommerce.database.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemJpaRepository extends JpaRepository<CartItemEntity, Integer> {

    List<CartItemEntity> findByUserId(Integer userId);

    Optional<CartItemEntity> findByUserIdAndProductId(Integer userId, Integer productId);

    void deleteByUserId(Integer userId);

    @Modifying
    @Query("DELETE FROM CartItemEntity c WHERE c.product.id = :productId")
    void deleteByProductId(@Param("productId") Integer productId);
}
