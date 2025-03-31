package com.ecommerce.security.exception;


import com.ecommerce.api.dto.ExceptionMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .errorId(UUID.randomUUID().toString())
                .status(HttpServletResponse.SC_UNAUTHORIZED)
                .message("Unauthorized access: " + authException.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();

        response.getWriter().write(new ObjectMapper().writeValueAsString(exceptionMessage));
    }
}
