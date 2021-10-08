package org.prgrms.orderApp.common;

import lombok.Builder;
import lombok.Getter;

import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;

@Getter
@Builder
public class BasicResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String message;
    private final Object data;

    public static ResponseEntity<BasicResponse> generateResponse(BasicCode basicCode, Object responseObj) {

        return ResponseEntity
                .status(basicCode.getHttpStatus())
                .body(BasicResponse.builder()
                        .status(basicCode.getHttpStatus().value())
                        .message(basicCode.getErrorMessage())
                        .data(responseObj)
                        .build()
                );
    }

}
