package com.prgrms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VoucherExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> notUpdateExHandle(NotUpdateException e) {

        ErrorResult errorResult = new ErrorResult("NotUpdate", e.getMessage());
        return new ResponseEntity<>(errorResult,  HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> discountLimitExHandle(DiscountLimitException e) {

        ErrorResult errorResult = new ErrorResult("Limit", e.getMessage());
        return new ResponseEntity<>(errorResult,  HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> negativeExHandle(NegativeArgumentException e) {

        ErrorResult errorResult = new ErrorResult("Negative", e.getMessage());
        return new ResponseEntity<>(errorResult,  HttpStatus.BAD_REQUEST);
    }

}
