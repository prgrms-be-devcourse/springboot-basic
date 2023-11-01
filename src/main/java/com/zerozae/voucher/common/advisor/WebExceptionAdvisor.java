package com.zerozae.voucher.common.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@Slf4j
@ControllerAdvice(annotations = Controller.class)
public class WebExceptionAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationException(Model model, MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
        model.addAttribute("error", errorMessage);
        log.warn("ErrorMessage = {}", errorMessage);
        return "/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception e) {
        model.addAttribute("error", e.getMessage());
        log.warn("ErrorMessage = {}", e.getMessage());
        return "/error";
    }
}
