package com.ecommerce.api.controller;

import com.ecommerce.api.dto.ExceptionMessage;
import com.ecommerce.business.exception.ProcessingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Map<Class<?>, HttpStatus> EXCEPTION_STATUS = Map.of(
            EntityNotFoundException.class, HttpStatus.NOT_FOUND,
            ProcessingException.class, HttpStatus.BAD_REQUEST
    );

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception ex,
            Object body,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode statusCode,
            @NonNull WebRequest request
    ) {
        final String errorId = generateErrorId(ex, statusCode);

        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .errorId(errorId)
                .status(statusCode.value())
                .message(ex.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();

        return super.handleExceptionInternal(ex, exceptionMessage, headers, statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException exception,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode statusCode,
            @NonNull WebRequest request) {

        final String errorId = generateErrorId(exception, statusCode);

        Map<String, String> errors = exception.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> Optional
                                .ofNullable(fieldError.getDefaultMessage()).orElse("Unknown validation error"),
                        (existing, replacement) -> existing
                ));

        return ResponseEntity.status(statusCode).body(
                Map.of(
                        "errorId", errorId,
                        "status", statusCode.value(),
                        "message", "Validation failed",
                        "errors", errors,
                        "timestamp", System.currentTimeMillis()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(final Exception exception) {
        return doHandle(exception, getHttpStatusFromException(exception.getClass()));
    }

    private ResponseEntity<Object> doHandle(final Exception exception, final HttpStatusCode status) {
        final String errorId = generateErrorId(exception, status);

        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .errorId(errorId)
                .status(status.value())
                .message(exception.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity
                .status(status)
                .body(exceptionMessage);
    }

    private HttpStatusCode getHttpStatusFromException(final Class<?> exception) {
        return EXCEPTION_STATUS.getOrDefault(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static String generateErrorId(Exception ex, HttpStatusCode statusCode) {
        final String errorId = UUID.randomUUID().toString();
        log.error("Exception: ID={}, HttpStatus={}, Message={}", errorId, statusCode, ex.getMessage());
        return errorId;
    }
}
