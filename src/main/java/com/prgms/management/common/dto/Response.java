package com.prgms.management.common.dto;

public record Response(
    Integer code,
    String message,
    Object data
) {
    
}
