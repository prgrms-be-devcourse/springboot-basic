package org.programmers.springbootbasic.voucher.controller;

import javassist.NotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public String notFoundHandler() {
        return "404-NotFound";
    }

    @ExceptionHandler(Exception.class)
    public String internalServerErrorHandler() {
        return "500-error";
    }
}
