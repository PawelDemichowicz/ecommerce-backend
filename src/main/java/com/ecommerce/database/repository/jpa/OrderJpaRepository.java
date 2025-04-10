package com.ecommerce.database.repository.jpa;

import com.ecommerce.database.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Integer> {

    List<OrderEntity> findByUserId(Integer userId);

    Optional<OrderEntity> findByIdAndUserId(Integer id, Integer userId);
}
