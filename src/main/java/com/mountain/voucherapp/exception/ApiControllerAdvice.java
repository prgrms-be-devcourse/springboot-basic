package com.mountain.voucherapp.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiControllerAdvice {
    private static final String INTERNAL_SERVER_ERROR_BODY = "Internal Server Error";

    @ExceptionHandler({IllegalArgumentException.class, JdbcUpdateNotExecuteException.class})
    public ResponseEntity<String> internalServerError() {
        return ResponseEntity.internalServerError().body(INTERNAL_SERVER_ERROR_BODY);
    }
}
