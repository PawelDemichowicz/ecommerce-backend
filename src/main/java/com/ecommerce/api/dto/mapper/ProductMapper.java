package com.ecommerce.api.dto.mapper;

import com.ecommerce.api.dto.ProductDTO;
import com.ecommerce.business.domain.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO mapToDTO(Product product);
}
