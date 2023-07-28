package com.tangerine.voucher_system.application.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VoucherSystemExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> internetServerErrorExceptionHandler(Exception e) {
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Void> applicationErrorExceptionHandler(InvalidDataException e) {
        return ResponseEntity.badRequest().build();
    }

}
