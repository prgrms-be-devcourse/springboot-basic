package org.prgrms.kdt.exception;

import lombok.Builder;

@Builder
public class ErrorResponse {

    private final String message;

    public static ErrorResponse of(ErrorMessage errorMessage) {
        return ErrorResponse.builder()
                .message(errorMessage.name())
                .build();
    }

    public ErrorResponse(String message) {
        this.message = message;
    }
}
