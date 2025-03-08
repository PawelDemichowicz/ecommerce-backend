package com.ecommerce.database.repository;

import com.ecommerce.business.domain.User;
import com.ecommerce.database.entity.mapper.UserEntityMapper;
import com.ecommerce.database.repository.jpa.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;

    public Optional<User> findById(Long userId) {
        return userJpaRepository.findById(userId)
                .map(userEntityMapper::mapFromEntity);
    }
}
