package com.ecommerce.api.dto;

import com.ecommerce.api.dto.response.CartItemResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemsDTO {

    List<CartItemResponseDTO> cartItems;
}
