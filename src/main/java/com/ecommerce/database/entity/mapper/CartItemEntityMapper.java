package com.ecommerce.database.entity.mapper;

import com.ecommerce.business.domain.CartItem;
import com.ecommerce.database.entity.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemEntityMapper {

    CartItem mapFromEntity(CartItemEntity cartItemEntity);

    CartItemEntity mapToEntity(CartItem cartItem);
}
