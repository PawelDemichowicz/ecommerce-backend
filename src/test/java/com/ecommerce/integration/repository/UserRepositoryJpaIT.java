package com.ecommerce.integration.repository;

import com.ecommerce.business.domain.User;
import com.ecommerce.database.entity.mapper.UserEntityMapperImpl;
import com.ecommerce.database.repository.UserRepository;
import com.ecommerce.integration.repository.configuration.AbstractJpaIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static com.ecommerce.util.DomainFixtures.someUser1;
import static org.assertj.core.api.Assertions.assertThat;

@Import({UserRepository.class, UserEntityMapperImpl.class})
class UserRepositoryJpaIT extends AbstractJpaIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindUserById() {
        // given
        User user = someUser1();

        // when
        userRepository.save(user);

        // then
        Optional<User> foundByEmail = userRepository.findByEmail("alice@example.com");
        assertThat(foundByEmail).isPresent();

        Optional<User> foundById = userRepository.findById(foundByEmail.get().getId());
        assertThat(foundById).isPresent();
        assertThat(foundById.get().getEmail()).isEqualTo("alice@example.com");
        assertThat(foundById.get().getUsername()).isEqualTo("alice_wonder");
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        // when
        Optional<User> result = userRepository.findByEmail("nonexistent@example.com");

        // then
        assertThat(result).isEmpty();
    }
}