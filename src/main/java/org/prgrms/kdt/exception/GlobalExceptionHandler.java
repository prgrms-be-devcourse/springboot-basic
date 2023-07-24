package org.prgrms.kdt.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.prgrms.kdt.domain.voucher.VoucherException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<?> defaultExceptionHandler(Exception e) {
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(VoucherException.class)
    public ResponseEntity<?> voucherExceptionHandler(VoucherException e) {
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }
}
