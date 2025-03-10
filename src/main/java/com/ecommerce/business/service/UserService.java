package com.ecommerce.business.service;

import com.ecommerce.business.domain.User;
import com.ecommerce.business.exception.NotFoundException;
import com.ecommerce.database.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("Could not find user by id: [%s]".formatted(userId));
        }
        return user.get();
    }
}
