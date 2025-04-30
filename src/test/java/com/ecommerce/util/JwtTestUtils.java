package com.ecommerce.util;

import com.ecommerce.business.domain.User;
import com.ecommerce.database.entity.enums.Role;
import com.ecommerce.security.util.CustomUserDetails;
import com.ecommerce.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTestUtils {

    private final JwtUtil jwtUtil;

    public String generateTokenForUser(Integer userId, String email) {
        User user = User.builder()
                .id(userId)
                .email(email)
                .password("$2a$12$PAj3iEy87jxVBnszAH9k5uqmkoZo4HVkh2NR")
                .role(Role.USER)
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(user);
        return jwtUtil.generateToken(userDetails);
    }
}
