package org.programmers.springorder.exception;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Profile("prod")
@ControllerAdvice
public class PageExceptionHandler {

    @ExceptionHandler(VoucherException.class)
    public String applicationHandler(VoucherException e){
        return "no-voucher";
    }

    @ExceptionHandler(RuntimeException.class)
    public String applicationHandler(RuntimeException e){
        return "no-voucher";
    }
}
