package com.ecommerce.api.controller;

import com.ecommerce.api.dto.CartItemsDTO;
import com.ecommerce.api.dto.mapper.CartItemMapper;
import com.ecommerce.api.dto.request.CartItemRequestDTO;
import com.ecommerce.api.dto.response.CartItemResponseDTO;
import com.ecommerce.business.domain.CartItem;
import com.ecommerce.business.service.CartService;
import com.ecommerce.security.util.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(CartController.API_CART)
public class CartController {

    public static final String API_CART = "/cart";
    public static final String API_CART_ID = "/{cartItemId}";
    public static final String API_CART_CLEAR = "/clear";

    private final CartService cartService;
    private final CartItemMapper cartItemMapper;


    @GetMapping
    public ResponseEntity<CartItemsDTO> getAllCartItems(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Integer userId = userDetails.getUserId();
        CartItemsDTO cartItems = CartItemsDTO.builder()
                .cartItems(cartService.getCartItemsByUser(userId).stream()
                        .map(cartItemMapper::mapToResponseDTO)
                        .toList())
                .build();
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping
    public CartItemResponseDTO addToCart(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid CartItemRequestDTO cartItemDTO
    ) {
        Integer userId = userDetails.getUserId();
        CartItem savedCartItem = cartService.addToCart(userId, cartItemDTO.getProductId(), cartItemDTO.getQuantity());
        return cartItemMapper.mapToResponseDTO(savedCartItem);
    }

    @DeleteMapping(value = API_CART_ID)
    public void removeItemFromCart(
            @PathVariable Integer cartItemId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Integer userId = userDetails.getUserId();
        cartService.removeFromCart(userId, cartItemId);
    }

    @DeleteMapping(value = API_CART_CLEAR)
    public void removeAllItemsFromCart(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Integer userId = userDetails.getUserId();
        cartService.clearCart(userId);
    }
}
