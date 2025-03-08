package com.ecommerce.database.entity.mapper;

import com.ecommerce.business.domain.User;
import com.ecommerce.database.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {

    User mapFromEntity(UserEntity userEntity);
}
