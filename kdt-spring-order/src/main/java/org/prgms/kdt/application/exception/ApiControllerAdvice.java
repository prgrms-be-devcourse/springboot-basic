package org.prgms.kdt.application.exception;

import lombok.extern.slf4j.Slf4j;
import org.prgms.kdt.application.customer.exception.CustomerDuplicateKeyException;
import org.prgms.kdt.application.voucher.exception.VoucherDuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Todo : 예외처리 개선
@Slf4j
@RestControllerAdvice(basePackages = "org.prgms.kdt.application.voucher.controller.api")
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgumentHandler(IllegalArgumentException e) {
        log.error("exceptionHandler", e);
        return e.getMessage();
    }

    @ExceptionHandler({CustomerDuplicateKeyException.class, VoucherDuplicateKeyException.class})
    public String duplicateKeyHandler(RuntimeException e) {
        log.error("exceptionHandler", e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public String exceptionHandler(RuntimeException e) {
        log.error("exceptionHandler", e);
        return e.getMessage();
    }

}
