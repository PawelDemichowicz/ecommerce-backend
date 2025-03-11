package com.ecommerce.database.repository;

import com.ecommerce.business.domain.CartItem;
import com.ecommerce.database.entity.CartItemEntity;
import com.ecommerce.database.entity.mapper.CartItemEntityMapper;
import com.ecommerce.database.repository.jpa.CartItemJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CartItemRepository {

    private final CartItemJpaRepository cartItemJpaRepository;
    private final CartItemEntityMapper cartItemEntityMapper;

    public Optional<CartItem> findById(Integer cartItemId) {
        return cartItemJpaRepository.findById(cartItemId)
                .map(cartItemEntityMapper::mapFromEntity);
    }

    public List<CartItem> findByUserId(Integer userId) {
        return cartItemJpaRepository.findByUserId(userId).stream()
                .map(cartItemEntityMapper::mapFromEntity)
                .toList();
    }

    public Optional<CartItem> findByUserAndProduct(Integer userId, Integer productId) {
        return cartItemJpaRepository.findByUserIdAndProductId(userId, productId)
                .map(cartItemEntityMapper::mapFromEntity);
    }

    public void saveCartItem(CartItem cartItem) {
        CartItemEntity cartItemToSave = cartItemEntityMapper.mapToEntity(cartItem);
        cartItemJpaRepository.save(cartItemToSave);
    }

    public void deleteById(Integer cartItemId) {
        cartItemJpaRepository.deleteById(cartItemId);
    }

    public void deleteByUserId(Integer userId) {
        cartItemJpaRepository.deleteByUserId(userId);
    }
}
