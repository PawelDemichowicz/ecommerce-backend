package com.ecommerce.database.entity.mapper;

import com.ecommerce.business.domain.Product;
import com.ecommerce.database.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductEntityMapper {

    Product mapFromEntity(ProductEntity productEntity);

    ProductEntity mapToEntity(Product product);
}
