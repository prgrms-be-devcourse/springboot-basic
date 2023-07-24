package com.prgmrs.voucher.global;

import com.prgmrs.voucher.exception.NoSuchVoucherTypeException;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({WrongRangeFormatException.class, DataAccessException.class})
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleNumberFormatException(NumberFormatException e) {
        return ResponseEntity.badRequest().body("invalid number was provided.");
    }

    @ExceptionHandler(NoSuchVoucherTypeException.class)
    public ResponseEntity<String> handleNoSuchVoucherTypeException(NoSuchVoucherTypeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
