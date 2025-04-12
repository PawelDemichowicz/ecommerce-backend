package com.ecommerce.api.dto.mapper;

import com.ecommerce.api.dto.request.ProductRequestDTO;
import com.ecommerce.api.dto.request.ProductUpdateRequestDTO;
import com.ecommerce.api.dto.response.ProductResponseDTO;
import com.ecommerce.business.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    Product mapFromRequestDTO(ProductRequestDTO productRequestDTO);

    Product mapFromUpdateRequestDTO(ProductUpdateRequestDTO productUpdateRequestDTO);

    ProductResponseDTO mapToResponseDTO(Product product);
}
