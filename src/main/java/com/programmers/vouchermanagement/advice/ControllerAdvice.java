package com.programmers.vouchermanagement.advice;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleInvalidMethodArgumentException(BindingResult bindingResult) {
        FieldError error = bindingResult.getFieldErrors()
                .get(0);
        String message = error.getField() + " exception - " + error.getDefaultMessage();
        return new ExceptionMessage(message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleIllegalArgumentException(IllegalArgumentException exception) {
        return new ExceptionMessage(exception.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessage handleNoSuchElementException(NoSuchElementException exception) {
        return new ExceptionMessage(exception.getMessage());
    }
}
