package com.ecommerce.database.repository.jpa;

import com.ecommerce.business.domain.User;
import com.ecommerce.database.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemJpaRepository extends JpaRepository<CartItemEntity, Long> {

    List<CartItemEntity> findByUser(User user);
}
