package com.ecommerce.database.entity.mapper;

import com.ecommerce.business.domain.Order;
import com.ecommerce.business.domain.OrderItem;
import com.ecommerce.database.entity.OrderEntity;
import com.ecommerce.database.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderEntityMapper {

    @Mapping(source = "orderItems", target = "orderItems", qualifiedByName = "mapOrderItems")
    Order mapFromEntity(OrderEntity orderEntity);

    OrderEntity mapToEntity(Order order);

    @Named("mapOrderItems")
    default List<OrderItem> mapOrderItems(List<OrderItemEntity> orderItemsEntity) {
        return orderItemsEntity.stream().map(this::mapFromEntity).toList();
    }

    @Mapping(target = "order", ignore = true)
    OrderItem mapFromEntity(OrderItemEntity orderItemEntity);
}
