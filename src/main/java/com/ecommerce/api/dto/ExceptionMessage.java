package com.ecommerce.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class ExceptionMessage {

    String errorId;
    int status;
    String message;
    long timestamp;
}
