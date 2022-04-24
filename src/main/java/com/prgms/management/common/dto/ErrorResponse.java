package com.prgms.management.common.dto;

public record ErrorResponse(
    Integer statusCode,
    String message
) {
    
}
