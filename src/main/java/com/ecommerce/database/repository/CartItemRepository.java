package com.ecommerce.database.repository;

import com.ecommerce.business.domain.CartItem;
import com.ecommerce.business.domain.User;
import com.ecommerce.database.entity.CartItemEntity;
import com.ecommerce.database.entity.mapper.CartItemEntityMapper;
import com.ecommerce.database.repository.jpa.CartItemJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CartItemRepository {

    private final CartItemJpaRepository cartItemJpaRepository;
    private final CartItemEntityMapper cartItemEntityMapper;

    public List<CartItem> findByUser(User user) {
        return cartItemJpaRepository.findByUser(user).stream()
                .map(cartItemEntityMapper::mapFromEntity)
                .toList();
    }

    public void saveCartItem(CartItem cartItem) {
        CartItemEntity cartItemToSave = cartItemEntityMapper.mapToEntity(cartItem);
        cartItemJpaRepository.save(cartItemToSave);
    }
}
