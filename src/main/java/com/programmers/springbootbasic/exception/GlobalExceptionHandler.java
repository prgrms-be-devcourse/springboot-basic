package com.programmers.springbootbasic.exception;

import com.programmers.springbootbasic.exception.exceptionClass.CustomException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
        CustomException.class,
    })
    public String handleUserException(CustomException ex, Model model) {
        model.addAttribute("error", ex.getErrorCode().getHttpStatus());
        model.addAttribute("message", ex.getMessage());
        return "customError";
    }
}
