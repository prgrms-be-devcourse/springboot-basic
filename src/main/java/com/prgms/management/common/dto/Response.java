package com.prgms.management.common.dto;

import org.springframework.http.HttpStatus;

public record Response(
    HttpStatus statusCode,
    String message,
    Object data
) {
    
}
