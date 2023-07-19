package com.devcourse.voucherapp.controller.web;

import static com.devcourse.voucherapp.entity.HomeMenu.CUSTOMER;
import static com.devcourse.voucherapp.entity.HomeMenu.VOUCHER;
import static com.devcourse.voucherapp.exception.ExceptionRule.NOT_ALLOWED_HTTP_METHOD;
import static com.devcourse.voucherapp.exception.ExceptionRule.UNEXPECTED_ERROR;

import com.devcourse.voucherapp.entity.HomeMenu;
import com.devcourse.voucherapp.exception.BusinessException;
import com.devcourse.voucherapp.exception.CustomerException;
import com.devcourse.voucherapp.exception.ExceptionRule;
import com.devcourse.voucherapp.exception.VoucherException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class WebExceptionHandler {

    private static final String ERROR_PAGE = "error/error";

    @ExceptionHandler(VoucherException.class)
    public String handleVoucherException(@ModelAttribute VoucherException e, Model model) {
        logBusinessError(e, VOUCHER);
        setBusinessErrorAttributes(e, model);

        return ERROR_PAGE;
    }

    @ExceptionHandler(CustomerException.class)
    public String handleCustomerException(@ModelAttribute CustomerException e, Model model) {
        logBusinessError(e, CUSTOMER);
        setBusinessErrorAttributes(e, model);

        return ERROR_PAGE;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public String handleHttpRequestMethodNotSupportedException(@ModelAttribute HttpRequestMethodNotSupportedException e, Model model) {
        logError(e, NOT_ALLOWED_HTTP_METHOD);
        setErrorAttributes(NOT_ALLOWED_HTTP_METHOD, model);

        return ERROR_PAGE;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus
    public String handleException(@ModelAttribute Exception e, Model model) {
        logError(e, UNEXPECTED_ERROR);
        setErrorAttributes(UNEXPECTED_ERROR, model);

        return ERROR_PAGE;
    }

    private void logBusinessError(BusinessException e, HomeMenu menu) {
        log.error("{} 발생 - {} | '{}' | 사용자 입력 : {}", e.getStatus(), menu.getName(), e.getMessage(), e.getCauseInput(), e);
    }

    private void logError(Exception e, ExceptionRule rule) {
        log.error("{} 발생 - '{}'", rule.getStatus(), rule.getMessage(), e);
    }

    private void setBusinessErrorAttributes(BusinessException e, Model model) {
        model.addAttribute("status", e.getStatus());
        model.addAttribute("message", e.getMessage());
        model.addAttribute("causeInput", e.getCauseInput());
    }

    private void setErrorAttributes(ExceptionRule rule, Model model) {
        model.addAttribute("status", rule.getStatus());
        model.addAttribute("message", rule.getMessage());
    }
}
