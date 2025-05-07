package com.ecommerce.unit.service;

import com.ecommerce.business.domain.CartItem;
import com.ecommerce.business.domain.Product;
import com.ecommerce.business.domain.User;
import com.ecommerce.business.exception.NotFoundException;
import com.ecommerce.business.exception.ProcessingException;
import com.ecommerce.business.service.CartService;
import com.ecommerce.business.service.ProductService;
import com.ecommerce.business.service.UserService;
import com.ecommerce.database.repository.CartItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CartService cartService;

    @Test
    void shouldReturnCartItemsByUser() {
        // given
        Integer userId = 1;
        List<CartItem> cartItems = List.of(mock(CartItem.class), mock(CartItem.class));
        when(cartItemRepository.findByUserId(userId)).thenReturn(cartItems);

        // when
        List<CartItem> result = cartService.getCartItemsByUser(userId);

        // then
        assertEquals(cartItems, result);
    }

    @Test
    void shouldAddNewProductToCartWhenNotExists() {
        // given
        Integer userId = 1;
        Integer productId = 11;
        Integer quantity = 2;

        User user = User.builder().id(userId).build();
        Product product = Product.builder().id(productId).build();
        CartItem expectedCartItem = CartItem.builder()
                .user(user)
                .product(product)
                .quantity(quantity)
                .build();

        when(userService.getUserById(userId)).thenReturn(user);
        when(productService.getProductById(productId)).thenReturn(product);
        doNothing().when(productService).validateStock(product, quantity);
        when(cartItemRepository.findByUserAndProduct(userId, productId)).thenReturn(Optional.empty());
        when(cartItemRepository.saveCartItem(any(CartItem.class))).thenReturn(expectedCartItem);

        // when
        CartItem result = cartService.addToCart(userId, productId, quantity);

        // then
        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(product, result.getProduct());
        assertEquals(quantity, result.getQuantity());
        verify(cartItemRepository).saveCartItem(any(CartItem.class));

    }

    @Test
    void shouldIncreaseQuantityWhenProductIsAlreadyInCart() {
        // given
        Integer userId = 1;
        Integer productId = 11;
        Integer existingQuantity = 2;
        Integer addedQuantity = 3;
        Integer expectedQuantity = 5;

        User user = User.builder().id(userId).build();
        Product product = Product.builder().id(productId).stock(10).build();
        CartItem existingCartItem = CartItem.builder()
                .id(3)
                .user(user)
                .product(product)
                .quantity(existingQuantity)
                .build();

        CartItem updatedCartItem = existingCartItem.withQuantity(expectedQuantity);

        when(userService.getUserById(userId)).thenReturn(user);
        when(productService.getProductById(productId)).thenReturn(product);
        doNothing().when(productService).validateStock(product, addedQuantity);
        when(cartItemRepository.findByUserAndProduct(userId, productId)).thenReturn(Optional.of(existingCartItem));
        when(cartItemRepository.saveCartItem(updatedCartItem)).thenReturn(updatedCartItem);

        // when
        CartItem result = cartService.addToCart(userId, productId, addedQuantity);

        // then
        assertEquals(expectedQuantity, result.getQuantity());
        verify(cartItemRepository).saveCartItem(updatedCartItem);
    }

    @Test
    void shouldThrowExceptionWhenNotEnoughStock() {
        // given
        Integer userId = 1;
        Integer productId = 11;
        Integer quantity = 50;

        User user = User.builder().id(userId).build();
        Product product = Product.builder().id(productId).stock(10).build();

        when(userService.getUserById(userId)).thenReturn(user);
        when(productService.getProductById(productId)).thenReturn(product);
        doCallRealMethod().when(productService).validateStock(product, quantity);

        // when & then
        Throwable exception = assertThrows(ProcessingException.class,
                () -> cartService.addToCart(userId, productId, quantity));
        assertEquals("Not enough stock for product with id: [%s]".formatted(product.getId()),
                exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // given
        Integer userId = 1;
        Integer productId = 11;
        Integer quantity = 2;

        when(userService.getUserById(userId))
                .thenThrow(new NotFoundException("Could not find user by id: [%s]".formatted(userId)));

        // when & then
        Throwable exception = assertThrows(NotFoundException.class,
                () -> cartService.addToCart(userId, productId, quantity));
        assertEquals("Could not find user by id: [%s]".formatted(userId), exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        // given
        Integer userId = 1;
        Integer productId = 11;
        Integer quantity = 2;

        User user = User.builder().id(userId).build();
        when(userService.getUserById(userId)).thenReturn(user);
        when(productService.getProductById(productId))
                .thenThrow(new NotFoundException("Could not find product by id: [%s]".formatted(productId)));

        // when & then
        Throwable exception = assertThrows(NotFoundException.class,
                () -> cartService.addToCart(userId, productId, quantity));
        assertEquals("Could not find product by id: [%s]".formatted(productId), exception.getMessage());
    }

    @Test
    void shouldRemoveCartItemWhenUserOwnsIt() {
        // given
        Integer userId = 1;
        Integer cartItemId = 10;

        User user = User.builder().id(userId).build();
        CartItem existingCartItem = CartItem.builder()
                .id(cartItemId)
                .user(user)
                .build();

        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.of(existingCartItem));

        // when
        cartService.removeFromCart(userId, cartItemId);

        // then
        verify(cartItemRepository).deleteById(cartItemId);
    }

    @Test
    void shouldThrowExceptionWhenCartItemNotFoundOnRemove() {
        // given
        Integer userId = 1;
        Integer cartItemId = 10;
        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.empty());

        // when & then
        Throwable exception = assertThrows(NotFoundException.class,
                () -> cartService.removeFromCart(userId, cartItemId));
        assertEquals("Could not find cart item by id: [%s]".formatted(cartItemId), exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCartItemNotOwnedByUser() {
        // given
        Integer userId = 1;
        Integer cartItemId = 10;

        User otherUser = User.builder().id(12).build();
        CartItem existingCartItem = CartItem.builder()
                .id(cartItemId)
                .user(otherUser)
                .build();

        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.of(existingCartItem));

        // when & then
        Throwable exception = assertThrows(NotFoundException.class,
                () -> cartService.removeFromCart(userId, cartItemId));
        assertEquals("Could not find cart item by id: [%s]".formatted(cartItemId), exception.getMessage());
    }

    @Test
    void shouldClearCartWhenItemsExist() {
        // given
        Integer userId = 1;
        List<CartItem> cartItems = List.of(mock(CartItem.class));
        when(cartItemRepository.findByUserId(userId)).thenReturn(cartItems);

        // when
        cartService.clearCart(userId);

        // then
        verify(cartItemRepository).deleteByUserId(userId);
    }

    @Test
    void shouldThrowExceptionWhenClearingEmptyCart() {
        // given
        Integer userId = 1;
        when(cartItemRepository.findByUserId(userId)).thenReturn(List.of());

        // when & then
        // when & then
        Throwable exception = assertThrows(ProcessingException.class,
                () -> cartService.clearCart(userId));
        assertEquals("Cart is already empty", exception.getMessage());
    }
}