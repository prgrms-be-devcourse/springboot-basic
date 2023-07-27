package org.devcourse.springbasic.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.devcourse.springbasic.global.exception.custom.DuplicateEmailException;
import org.devcourse.springbasic.global.exception.custom.DuplicateVoucherException;
import org.devcourse.springbasic.global.exception.custom.OutOfLimitsDiscountRateException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice(annotations = Controller.class)
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public String handleDuplicateEmailException(DuplicateEmailException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("errorCode", HttpStatus.CONFLICT.value());
        return "customer-create-form";
    }

    @ExceptionHandler(OutOfLimitsDiscountRateException.class)
    public String handleOutOfLimitsDiscountRateException(OutOfLimitsDiscountRateException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("errorCode", HttpStatus.BAD_REQUEST.value());
        return "voucher-create-form";
    }

    @ExceptionHandler(DuplicateVoucherException.class)
    public String handleDuplicateVoucherException(DuplicateVoucherException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("errorCode", HttpStatus.CONFLICT.value());
        return "voucher-create-form";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("errorCode", HttpStatus.CONFLICT.value());
        return "error-page";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("errorCode", HttpStatus.NOT_FOUND.value());
        return "error-page";
    }

    @ExceptionHandler(RuntimeException.class)
    public String runtimeExHandle(RuntimeException e, Model model) {
        log.error(e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "error-page";
    }
}