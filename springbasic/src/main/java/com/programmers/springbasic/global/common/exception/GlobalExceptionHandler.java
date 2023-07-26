package com.programmers.springbasic.global.common.exception;

import com.programmers.springbasic.domain.customer.exception.CustomerException;
import com.programmers.springbasic.domain.voucher.exception.VoucherException;
import com.programmers.springbasic.global.common.response.model.CommonResult;
import com.programmers.springbasic.global.common.response.service.ResponseFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        return ResponseFactory.getFailResult(ErrorCode.UNKNOWN.getCode(), ErrorCode.UNKNOWN.getErrorMessage());
    }

    @ExceptionHandler(CustomerException.class)
    protected CommonResult handleCustomerException(HttpServletRequest request, CustomerException e) {
        ErrorCode errorCode = e.getErrorCode();

        return ResponseFactory.getFailResult(errorCode.getCode(), errorCode.getErrorMessage());
    }

    @ExceptionHandler(VoucherException.class)
    protected CommonResult handleVoucherException(HttpServletRequest request, VoucherException e) {
        ErrorCode errorCode = e.getErrorCode();

        return ResponseFactory.getFailResult(errorCode.getCode(), errorCode.getErrorMessage());
    }
}
