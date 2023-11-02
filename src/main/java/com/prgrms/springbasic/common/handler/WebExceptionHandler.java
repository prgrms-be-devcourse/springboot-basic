package com.prgrms.springbasic.common.handler;

import com.prgrms.springbasic.common.exception.InvalidValueException;
import com.prgrms.springbasic.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = Controller.class)
public class WebExceptionHandler {

    private static final String ERROR = "error";

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleVoucherNotFoundException(ResourceNotFoundException ex, Model model) {
        model.addAttribute(ERROR, ex.getMessage());
        return ERROR;
    }

    @ExceptionHandler(InvalidValueException.class)
    public String handleInvalidTypeException(InvalidValueException ex, Model model) {
        model.addAttribute(ERROR, ex.getMessage());
        return ERROR;
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {
        model.addAttribute(ERROR, ex.getMessage());
        return ERROR;
    }
}
