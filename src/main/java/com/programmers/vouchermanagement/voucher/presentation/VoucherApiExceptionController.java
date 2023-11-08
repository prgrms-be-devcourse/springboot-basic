package com.programmers.vouchermanagement.voucher.presentation;

import com.programmers.vouchermanagement.voucher.exception.IllegalDiscountException;
import com.programmers.vouchermanagement.voucher.exception.VoucherNotFoundException;
import com.programmers.vouchermanagement.voucher.exception.VoucherNotUpdatedException;
import com.programmers.vouchermanagement.voucher.exception.VoucherTypeNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VoucherApiExceptionController {

    @ExceptionHandler(VoucherNotFoundException.class)
    public String catchVoucherNotFoundException(VoucherNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(VoucherNotUpdatedException.class)
    public String catchVoucherNotUpdateException(VoucherNotUpdatedException e) {
        return e.getMessage();
    }

    @ExceptionHandler(IllegalDiscountException.class)
    public String catchIllegalDiscountException(IllegalDiscountException e) {
        return e.getMessage();
    }

    @ExceptionHandler(VoucherTypeNotFoundException.class)
    public String catchVoucherTypeNotFoundException(VoucherTypeNotFoundException e) {
        return e.getMessage();
    }
}
