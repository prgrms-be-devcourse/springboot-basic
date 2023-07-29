package com.tangerine.voucher_system.application.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VoucherSystemExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> internetServerErrorExceptionHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<String> invalidDataExceptionHandler(InvalidDataException e) {
        return ResponseEntity.badRequest().body("Invalid Data: " + e.getMessage());
    }

    @ExceptionHandler(SqlException.class)
    public ResponseEntity<String> sqlErrorExceptionHandler(SqlException e) {
        return ResponseEntity.badRequest().body("SQL Error: " + e.getMessage());
    }
}
