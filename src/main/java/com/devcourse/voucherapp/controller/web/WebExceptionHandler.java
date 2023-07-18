package com.devcourse.voucherapp.controller.web;

import static com.devcourse.voucherapp.exception.ExceptionRule.UNEXPECTED_EXCEPTION;

import com.devcourse.voucherapp.exception.CustomerException;
import com.devcourse.voucherapp.exception.VoucherException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(VoucherException.class)
    public String handleVoucherException(@ModelAttribute VoucherException e, Model model) {
        log.error("할인권 메뉴에서 예외 발생 - {} | '{}' | 사용자 입력 : {}", e.getStatus(), e.getMessage(), e.getCauseInput(), e);
        model.addAttribute("status", e.getStatus());
        model.addAttribute("message", e.getMessage());
        model.addAttribute("causeInput", e.getCauseInput());

        return "error/customError";
    }

    @ExceptionHandler(CustomerException.class)
    public String handleCustomerException(@ModelAttribute CustomerException e, Model model) {
        log.error("고객 메뉴에서 예외 발생 - {} | '{}' | 사용자 입력 : {}", e.getStatus(), e.getMessage(), e.getCauseInput(), e);
        model.addAttribute("status", e.getStatus());
        model.addAttribute("message", e.getMessage());
        model.addAttribute("causeInput", e.getCauseInput());

        return "error/customError";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus
    public String handleUnexpectedException(@ModelAttribute Exception e, Model model) {
        log.error("원인 불명의 예외 발생 - {} '{}'", UNEXPECTED_EXCEPTION.getStatus(), e.getMessage(), e);
        model.addAttribute("status", UNEXPECTED_EXCEPTION.getStatus());
        model.addAttribute("message", UNEXPECTED_EXCEPTION.getMessage());

        return "error/customError";
    }
}
