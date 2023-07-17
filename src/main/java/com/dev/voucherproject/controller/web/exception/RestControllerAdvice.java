package com.dev.voucherproject.controller.web.exception;


import com.dev.voucherproject.controller.web.response.Response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response<String>> runtimeExceptionHandler(RuntimeException e) {
        return ResponseEntity
                .badRequest()
                .body(Response.error(e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response<String>> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return ResponseEntity
                .badRequest()
                .body(Response.error(e.getMessage()));
    }
}
