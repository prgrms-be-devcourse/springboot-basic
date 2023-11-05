package com.programmers.vouchermanagement.advice;

import java.util.NoSuchElementException;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralAdvice {

    @ExceptionHandler({IllegalArgumentException.class, NoSuchElementException.class, RuntimeException.class})
    public String renderErrorPage(RuntimeException exception, Model model) {
        model.addAttribute("message", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleInvalidMethodArgumentException(BindingResult bindingResult, Model model) {
        FieldError error = bindingResult.getFieldErrors()
                .get(0);
        String message = error.getField() + " exception - " + error.getDefaultMessage();
        model.addAttribute("message", message);
        return "error";
    }
}
