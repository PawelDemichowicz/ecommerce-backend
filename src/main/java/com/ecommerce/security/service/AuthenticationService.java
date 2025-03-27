package com.ecommerce.security.service;

import com.ecommerce.business.domain.User;
import com.ecommerce.business.exception.NotFoundException;
import com.ecommerce.database.entity.enums.Role;
import com.ecommerce.database.repository.UserRepository;
import com.ecommerce.security.auth.dto.AuthenticateRequest;
import com.ecommerce.security.auth.dto.AuthenticationResponse;
import com.ecommerce.security.auth.dto.RegisterRequest;
import com.ecommerce.security.util.CustomUserDetails;
import com.ecommerce.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = buildUser(request);
        userRepository.save(user);
        return buildToken(user);
    }

    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new NotFoundException("Could not find user by email: [%s]".formatted(request.getEmail())));
        return buildToken(user);
    }

    private User buildUser(RegisterRequest request) {
        return User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
    }

    private AuthenticationResponse buildToken(User user) {
        CustomUserDetails userDetails = new CustomUserDetails(user);
        return AuthenticationResponse.builder()
                .token(jwtUtil.generateToken(userDetails))
                .build();
    }
}
