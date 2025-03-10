package com.ecommerce.business.service;

import com.ecommerce.business.domain.CartItem;
import com.ecommerce.business.domain.Product;
import com.ecommerce.business.domain.User;
import com.ecommerce.business.exception.NotFoundException;
import com.ecommerce.business.exception.ProcessingException;
import com.ecommerce.database.repository.CartItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;

    private final ProductService productService;
    private final UserService userService;

    public CartItem getCartItem(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NotFoundException("Could not find cart by id: [%s]".formatted(cartItemId)));
    }

    @Transactional
    public List<CartItem> getCartItemsByUser(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    @Transactional
    public void addToCart(Long userId, Long productId, Integer quantity) {
        User user = userService.getUserById(userId);
        Product product = productService.getProductById(productId);
        Optional<CartItem> existingCartItem = cartItemRepository.findByUserAndProduct(userId, productId);

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            CartItem newCartItem = cartItem.withQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.saveCartItem(newCartItem);
        } else {
            CartItem cartItem = CartItem.builder()
                    .user(user)
                    .product(product)
                    .quantity(quantity)
                    .build();
            cartItemRepository.saveCartItem(cartItem);
        }
    }

    @Transactional
    public void removeFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Transactional
    public void clearCart(Long userId) {
        List<CartItem> cartItems = getCartItemsByUser(userId);

        if (cartItems.isEmpty()) {
            throw new ProcessingException("Cart is already empty");
        }

        cartItemRepository.deleteByUserId(userId);
    }
}
