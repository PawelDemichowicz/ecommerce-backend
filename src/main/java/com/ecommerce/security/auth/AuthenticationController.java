package com.ecommerce.security.auth;

import com.ecommerce.security.auth.dto.AuthenticateRequest;
import com.ecommerce.security.auth.dto.AuthenticationResponse;
import com.ecommerce.security.auth.dto.RegisterRequest;
import com.ecommerce.security.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(AuthenticationController.API_AUTH)
public class AuthenticationController {

    public static final String API_AUTH = "/auth";
    public static final String API_REGISTER = "/register";
    public static final String API_AUTHENTICATE = "/login";

    private final AuthenticationService authenticationService;

    @PostMapping(value = API_REGISTER)
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping(value = API_AUTHENTICATE)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticateRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
