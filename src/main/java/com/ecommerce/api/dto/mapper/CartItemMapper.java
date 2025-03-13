package com.ecommerce.api.dto.mapper;

import com.ecommerce.api.dto.CartItemDTO;
import com.ecommerce.api.dto.ProductDTO;
import com.ecommerce.business.domain.CartItem;
import com.ecommerce.business.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper {

    CartItemDTO mapToDTO(CartItem cartItem);

    CartItem mapFromDTO(CartItemDTO cartItemDTO);
}
