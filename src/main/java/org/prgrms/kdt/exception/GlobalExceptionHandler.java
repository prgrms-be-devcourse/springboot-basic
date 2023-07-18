package org.prgrms.kdt.exception;

import org.prgrms.kdt.domain.voucher.VoucherException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
class GlobalExceptionHandler {
    public static final String DEFAULT_EXCEPTION_VIEW = "exception";
    public static final String VOUCHER_EXCEPTION_VIEW = "vouchers/voucher_exception";

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultExceptionHandler(Exception e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ErrorMessage.valueOf(e.getMessage()));
        mav.setViewName(DEFAULT_EXCEPTION_VIEW);
        return mav;
    }


    @ExceptionHandler(VoucherException.class)
    public ModelAndView voucherExceptionHandler(VoucherException e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("voucher_exception", e.getErrorMessage());
        mav.setViewName(VOUCHER_EXCEPTION_VIEW);
        return mav;
    }
}
