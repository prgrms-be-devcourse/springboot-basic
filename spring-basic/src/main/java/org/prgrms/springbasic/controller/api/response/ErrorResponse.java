package org.prgrms.springbasic.controller.api.response;

import lombok.Builder;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, int statusCode, String path, String error, String errorMessage) {

    @Builder
    public ErrorResponse {

    }

    public static ErrorResponse responseOf(LocalDateTime timestamp, int statusCode, String path, Exception e) {
        return ErrorResponse.builder()
                .timestamp(timestamp)
                .statusCode(statusCode)
                .path(path)
                .error(e.getCause().getClass().getSimpleName())
                .errorMessage(e.getCause().getMessage())
                .build();
    }
}
