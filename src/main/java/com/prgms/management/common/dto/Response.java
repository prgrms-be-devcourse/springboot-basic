package com.prgms.management.common.dto;

public record Response(
    Integer statusCode,
    String message,
    Object data
) {

}
