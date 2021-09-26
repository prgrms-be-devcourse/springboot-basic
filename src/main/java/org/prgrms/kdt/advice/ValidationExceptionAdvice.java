package org.prgrms.kdt.advice;

import org.prgrms.kdt.exception.BadRequestException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ValidationExceptionAdvice {

    @ExceptionHandler(BadRequestException.class)
    public String handleBadRequestException() {
        return "error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleBindException() {
        return "error";
    }
}
