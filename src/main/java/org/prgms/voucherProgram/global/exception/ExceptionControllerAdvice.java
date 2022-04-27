package org.prgms.voucherProgram.global.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler
    public String handleCustomerException(IllegalArgumentException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }

    @ExceptionHandler
    public String handleAllException(Exception e, Model model) {
        logger.error(e.getMessage());
        model.addAttribute("error", e.getMessage());
        return "error";
    }
}
