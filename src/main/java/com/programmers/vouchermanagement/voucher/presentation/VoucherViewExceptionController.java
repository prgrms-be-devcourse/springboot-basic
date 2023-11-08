package com.programmers.vouchermanagement.voucher.presentation;

import com.programmers.vouchermanagement.voucher.exception.IllegalDiscountException;
import com.programmers.vouchermanagement.voucher.exception.VoucherNotFoundException;
import com.programmers.vouchermanagement.voucher.exception.VoucherNotUpdatedException;
import com.programmers.vouchermanagement.voucher.exception.VoucherTypeNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class VoucherViewExceptionController {

    @ExceptionHandler(VoucherNotFoundException.class)
    public String catchVoucherNotFoundException(VoucherNotFoundException e, Model model) {

        model.addAttribute("errorMessage", e.getMessage());

        return "exception-detail";
    }

    @ExceptionHandler(VoucherNotUpdatedException.class)
    public String catchVoucherNotUpdateException(VoucherNotUpdatedException e, Model model) {

        model.addAttribute("errorMessage", e.getMessage());

        return "exception-detail";
    }

    @ExceptionHandler(IllegalDiscountException.class)
    public String catchIllegalDiscountException(IllegalDiscountException e, Model model) {

        model.addAttribute("errorMessage", e.getMessage());

        return "exception-detail";
    }

    @ExceptionHandler(VoucherTypeNotFoundException.class)
    public String catchVoucherTypeNotFoundException(VoucherTypeNotFoundException e, Model model) {

        model.addAttribute("errorMessage", e.getMessage());

        return "exception-detail";
    }
}
