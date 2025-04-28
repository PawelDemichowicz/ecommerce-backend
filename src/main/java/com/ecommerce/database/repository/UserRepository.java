package com.ecommerce.database.repository;

import com.ecommerce.business.domain.User;
import com.ecommerce.database.entity.UserEntity;
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

    public Optional<User> findById(Integer userId) {
        return userJpaRepository.findById(userId)
                .map(userEntityMapper::mapFromEntity);
    }

    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(userEntityMapper::mapFromEntity);
    }

    public User save(User user) {
        UserEntity userToSave = userEntityMapper.mapToEntity(user);
        return userEntityMapper.mapFromEntity(userJpaRepository.save(userToSave));
    }
}
