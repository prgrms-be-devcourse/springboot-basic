package org.prgrms.kdt.controller;

import org.prgrms.kdt.error.VoucherNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class VoucherErrorController {

    @ExceptionHandler(VoucherNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String notFoundVoucherPage() {
        return "not-found";
    }

}
