package com.ecommerce.api.dto.mapper;

import com.ecommerce.api.dto.response.CartItemResponseDTO;
import com.ecommerce.business.domain.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper {

    CartItemResponseDTO mapToResponseDTO(CartItem cartItem);
}
