package com.ecommerce.database.entity.mapper;

import com.ecommerce.business.domain.OrderItem;
import com.ecommerce.database.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemEntityMapper {

    OrderItem mapFromEntity(OrderItemEntity orderItemEntity);

    OrderItemEntity mapToEntity(OrderItem orderItem);

}
