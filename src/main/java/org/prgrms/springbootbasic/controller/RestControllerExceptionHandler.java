package org.prgrms.springbootbasic.controller;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbootbasic.dto.ErrorResult;
import org.prgrms.springbootbasic.exception.*;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@Slf4j
@RestControllerAdvice(assignableTypes = {CustomerRestController.class, VoucherRestController.class})
public class RestControllerExceptionHandler {
    @ResponseStatus(value = NOT_FOUND)
    @ExceptionHandler
    public ErrorResult CustomerNotFountExceptionHandler(CustomerNotFoundException e) {
        log.error("[exception occurred] : {}", e.getMessage());
        return new ErrorResult("CustomerNotFound", e.getMessage());
    }

    @ResponseStatus(value = BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult DuplicateEmailExceptionHandler(DuplicatedEmailException e) {
        log.error("[exception occurred] : {}", e.getMessage());
        return new ErrorResult("CustomerNotFound", e.getMessage());
    }

    @ResponseStatus(value = BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult NotValidVoucherTypeExceptionHandler(NotValidVoucherTypeException e) {
        log.error("[exception occurred] : {}", e.getMessage());
        return new ErrorResult("notValidVoucherType", e.getMessage());
    }

    @ResponseStatus(value = NOT_FOUND)
    @ExceptionHandler
    public ErrorResult VoucherNotFountExceptionHandler(VoucherNotFoundException e) {
        log.error("[exception occurred] : {}", e.getMessage());
        return new ErrorResult("VoucherNotFound", e.getMessage());
    }

    @ResponseStatus(value = NOT_FOUND)
    @ExceptionHandler
    public ErrorResult InValidVoucherDateExceptionHandler(InValidVoucherDateException e) {
        log.error("[exception occurred] : {}", e.getMessage());
        return new ErrorResult("InValidVoucherDate", e.getMessage());
    }
}
