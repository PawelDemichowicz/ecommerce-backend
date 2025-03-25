package com.ecommerce.security.service;

import com.ecommerce.business.exception.NotFoundException;
import com.ecommerce.database.repository.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {

    private final UserJpaRepository userJpaRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userJpaRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
