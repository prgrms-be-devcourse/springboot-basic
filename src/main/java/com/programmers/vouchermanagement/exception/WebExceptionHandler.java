package com.programmers.vouchermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackages = "com.programmers.vouchermanagement.controller.web")
public class WebExceptionHandler {

    private final String ERROR_VIEW = "error";

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRuntimeException(RuntimeException e, Model model) {
        model.addAttribute(ERROR_VIEW, e.getMessage());
        return ERROR_VIEW;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e, Model model) {
        model.addAttribute(ERROR_VIEW, e.getMessage());
        return ERROR_VIEW;
    }
}
