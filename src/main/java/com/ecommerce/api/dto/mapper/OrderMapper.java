package com.ecommerce.api.dto.mapper;

import com.ecommerce.api.dto.OrderDTO;
import com.ecommerce.business.domain.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderDTO mapToDTO(Order order);
}
