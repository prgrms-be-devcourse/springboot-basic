package com.programmers.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MvcControllerAdvice {

    private final Logger log = LoggerFactory.getLogger(MvcControllerAdvice.class);

    @ExceptionHandler(RuntimeException.class)
    public String runtimeExceptionHandler(RuntimeException e, Model model) {
        log.error("Mvc exception", e.getMessage());

        model.addAttribute("message", e.getMessage());
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());

        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e, Model model) {
        log.error("Mvc exception", e.getMessage());

        model.addAttribute("message", e.getMessage());
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());

        return "error";
    }
}
