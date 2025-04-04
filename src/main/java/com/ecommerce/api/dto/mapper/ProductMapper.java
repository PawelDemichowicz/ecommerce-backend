package com.ecommerce.api.dto.mapper;

import com.ecommerce.api.dto.ProductRequestDTO;
import com.ecommerce.api.dto.ProductResponseDTO;
import com.ecommerce.business.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    Product mapFromRequestDTO(ProductRequestDTO productRequestDTO);

    ProductResponseDTO mapToResponseDTO(Product product);
}
