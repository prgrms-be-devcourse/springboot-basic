package org.prgrms.kdt.voucher.controller;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.kdt.exception.voucher.VoucherServerException;
import org.prgrms.kdt.exception.voucher.VoucherUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(assignableTypes = VoucherController.class)
public class VoucherExceptionControllerAdvice {

    @ExceptionHandler()
    public ResponseEntity<String> userHandler(VoucherUserException userException) {
        log.error("[customerExceptionHandler] => {}", userException);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userException.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> serverHandler(VoucherServerException serverException) {
        log.error("[customerExceptionHandler] => {}", serverException);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(serverException.getMessage());
    }
}
