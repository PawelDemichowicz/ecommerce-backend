package com.ecommerce.unit.service;

import com.ecommerce.business.domain.User;
import com.ecommerce.business.exception.NotFoundException;
import com.ecommerce.business.service.UserService;
import com.ecommerce.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnUserById() {
        // given
        int userId = 1;
        User user = User.builder().id(userId).build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        User result = userService.getUserById(userId);

        // then
        assertEquals(userId, result.getId());
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        // given
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when & then
        Throwable exception = assertThrows(NotFoundException.class,
                () -> userService.getUserById(userId));
        assertEquals("Could not find user by id: [%s]".formatted(userId), exception.getMessage());
    }
}