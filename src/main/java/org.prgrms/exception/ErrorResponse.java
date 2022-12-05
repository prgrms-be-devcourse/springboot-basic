package org.prgrms.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

public record ErrorResponse(String message, HttpStatus status) {

    @Builder
    public ErrorResponse {
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
            .message(errorCode.getMessage())
            .status(errorCode.getStatus())
            .build();
    }

}
