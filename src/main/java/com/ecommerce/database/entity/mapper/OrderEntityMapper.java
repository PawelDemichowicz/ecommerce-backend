package com.ecommerce.database.entity.mapper;

import com.ecommerce.business.domain.Order;
import com.ecommerce.database.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderEntityMapper {

    Order mapFromEntity(OrderEntity orderEntity);

    OrderEntity mapToEntity(Order order);
}
