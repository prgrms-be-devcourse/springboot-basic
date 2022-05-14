package org.programmers.springbootbasic.voucher.controller;

import javassist.NotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class VoucherControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public String notFoundHandler() {
        return "404-NotFound";
    }

    @ExceptionHandler(Exception.class)
    public String internalServerErrorHandler() {
        return "500-error";
    }
}
