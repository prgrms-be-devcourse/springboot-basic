package org.prgrms.kdt.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class VoucherWebExceptionHandler {

    @ExceptionHandler(GetResultFailedException.class)
    public String handleGetResultFailedException(GetResultFailedException exception, Model model) {
        model.addAttribute(exception.getMessage());
        return "voucher/exception/error";
    }

    @ExceptionHandler(NoVoucherException.class)
    public String handleNoVoucherException(NoVoucherException exception, Model model) {
        model.addAttribute(exception.getMessage());
        return "voucher/exception/noResult";
    }

    @ExceptionHandler(InvalidParameterException.class)
    public String handleInvalidParameterException(InvalidParameterException exception, Model model) {
        model.addAttribute(exception.getMessage());
        return "voucher/exception/nothingWork";
    }
}
