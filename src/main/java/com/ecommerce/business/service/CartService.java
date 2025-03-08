package com.ecommerce.business.service;

import com.ecommerce.business.domain.CartItem;
import com.ecommerce.business.domain.Product;
import com.ecommerce.business.domain.User;
import com.ecommerce.database.repository.CartItemRepository;
import com.ecommerce.database.repository.ProductRepository;
import com.ecommerce.database.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private final ProductService productService;
    private final UserService userService;

    public List<CartItem> getCartItemsByUser(Long userId) {
        User user = userService.getUserById(userId);
        return cartItemRepository.findByUser(user);
    }

    @Transactional
    public void addToCart(Long userId, Long productId, Integer quantity) {
        User user = userService.getUserById(userId);
        Product product = productService.getProductById(productId);

        CartItem cartItem = CartItem.builder()
                .user(user)
                .product(product)
                .quantity(quantity)
                .build();

        cartItemRepository.saveCartItem(cartItem);
    }
}
