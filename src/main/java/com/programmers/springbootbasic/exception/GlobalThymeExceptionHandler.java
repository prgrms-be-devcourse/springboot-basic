package com.programmers.springbootbasic.exception;

import com.programmers.springbootbasic.exception.exceptionClass.CustomException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalThymeExceptionHandler {

    @ExceptionHandler({
        CustomException.class,
    })
    public String handleUserException(CustomException ex, Model model) {
        System.out.println("GlobalThymeExceptionHandler.handleUserException");
        model.addAttribute("error", ex.getErrorCode().getHttpStatus());
        model.addAttribute("message", ex.getMessage());
        return "customError";
    }

    @ExceptionHandler({
        Exception.class,
    })
    public String handleException() {
        return "defaultError";
    }
}
