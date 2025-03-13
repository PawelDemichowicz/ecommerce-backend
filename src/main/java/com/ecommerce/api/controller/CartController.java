package com.ecommerce.api.controller;

import com.ecommerce.api.dto.CartItemDTO;
import com.ecommerce.api.dto.CartItemsDTO;
import com.ecommerce.api.dto.mapper.CartItemMapper;
import com.ecommerce.business.domain.CartItem;
import com.ecommerce.business.service.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping(CartController.API_CART)
public class CartController {

    public static final String API_CART = "/cart";
    public static final String API_CART_ID = "/{cartItemId}";
    public static final String API_CART_USER_ID = "/user/{userId}";
    public static final String API_CART_CLEAR = "/{userId}/clear";

    private final CartService cartService;
    private final CartItemMapper cartItemMapper;


    @GetMapping(value = API_CART_USER_ID)
    public ResponseEntity<CartItemsDTO> getAllCartItems(
            @PathVariable Integer userId
    ) {
        if (Objects.isNull(userId)) {
            return ResponseEntity.notFound().build();
        }

        CartItemsDTO cartItems = CartItemsDTO.builder()
                .cartItems(cartService.getCartItemsByUser(userId).stream()
                        .map(cartItemMapper::mapToDTO)
                        .toList())
                .build();
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping(value = API_CART_USER_ID)
    public CartItemDTO addToCart(
            @PathVariable Integer userId,
            @RequestBody @Valid CartItemDTO cartItemDTO
    ) {
        CartItem cartItem = cartItemMapper.mapFromDTO(cartItemDTO);
        cartService.addToCart(userId, cartItemDTO.getProduct().getId(), cartItemDTO.getQuantity());
        return cartItemMapper.mapToDTO(cartItem);
    }

    @DeleteMapping(value = API_CART_ID)
    public void removeItemFromCart(
            @PathVariable Integer cartItemId
    ) {
        cartService.removeFromCart(cartItemId);
    }

    @DeleteMapping(value = API_CART_CLEAR)
    public void removeAllItemsFromCart(
            @PathVariable Integer userId
    ) {
        cartService.clearCart(userId);
    }
}
