package com.devcourse.voucherapp.controller.web;

import static com.devcourse.voucherapp.entity.HomeMenu.CUSTOMER;
import static com.devcourse.voucherapp.entity.HomeMenu.VOUCHER;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.devcourse.voucherapp.entity.HomeMenu;
import com.devcourse.voucherapp.exception.BusinessException;
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

    private static final String UNEXPECTED_EXCEPTION_MESSAGE = "알 수 없는 에러가 발생했습니다. 이용에 불편함을 드려 죄송합니다.";
    private static final String CUSTOM_ERROR_PAGE = "error/customError";

    @ExceptionHandler(VoucherException.class)
    public String handleVoucherException(@ModelAttribute VoucherException e, Model model) {
        logError(e, VOUCHER);
        setExceptionAttributes(e, model);

        return CUSTOM_ERROR_PAGE;
    }

    @ExceptionHandler(CustomerException.class)
    public String handleCustomerException(@ModelAttribute CustomerException e, Model model) {
        logError(e, CUSTOMER);
        setExceptionAttributes(e, model);

        return CUSTOM_ERROR_PAGE;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus
    public String handleUnexpectedException(@ModelAttribute Exception e, Model model) {
        log.error("원인 불명의 예외 발생 - {} | '{}'", INTERNAL_SERVER_ERROR, e.getMessage(), e);
        model.addAttribute("status", INTERNAL_SERVER_ERROR);
        model.addAttribute("message", UNEXPECTED_EXCEPTION_MESSAGE);

        return CUSTOM_ERROR_PAGE;
    }

    private void logError(BusinessException e, HomeMenu menu) {
        log.error("{}에서 예외 발생 - {} | '{}' | 사용자 입력 : {}", menu.getName(), e.getStatus(), e.getMessage(), e.getCauseInput(), e);
    }

    private void setExceptionAttributes(BusinessException e, Model model) {
        model.addAttribute("status", e.getStatus());
        model.addAttribute("message", e.getMessage());
        model.addAttribute("causeInput", e.getCauseInput());
    }
}
