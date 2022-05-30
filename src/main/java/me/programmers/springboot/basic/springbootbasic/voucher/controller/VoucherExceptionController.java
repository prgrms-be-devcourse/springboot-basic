package me.programmers.springboot.basic.springbootbasic.voucher.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class VoucherExceptionController {

    @ExceptionHandler()
    private ResponseEntity<String> exceptionHandle(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
