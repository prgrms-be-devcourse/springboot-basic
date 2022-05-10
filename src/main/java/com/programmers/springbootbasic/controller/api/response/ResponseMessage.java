package com.programmers.springbootbasic.controller.api.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseMessage {

    public static ResponseEntity<CommonResponse> getCommonResponseEntity(Object content, String description) {
        return new ResponseEntity<>(CommonResponse.builder()
                .body(content)
                .description(description)
                .build(), HttpStatus.OK);
    }

    public static ResponseEntity<ErrorResponse> getErrorResponseEntity(HttpStatus errorCode, String cause) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(errorCode.toString())
                .errorCause(cause)
                .build(), errorCode);
    }
}
