package com.prgms.springbootbasic.exceptionhandler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class VoucherExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String voucherExceptionHandler(RuntimeException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "views/403";
    }

}
