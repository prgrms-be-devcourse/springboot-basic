package org.prgrms.springbasic.controller.api.response;

import lombok.Builder;
import org.prgrms.springbasic.controller.api.ErrorCode;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, int statusCode, String error, String errorMessage) {

    @Builder
    public ErrorResponse {

    }

    public static ErrorResponse responseOf(LocalDateTime timestamp, ErrorCode errorCode, String error) {
        return ErrorResponse.builder()
                .timestamp(timestamp)
                .statusCode(errorCode.getStatusCode())
                .error(error)
                .errorMessage(errorCode.getErrorMessage())
                .build();
    }
}