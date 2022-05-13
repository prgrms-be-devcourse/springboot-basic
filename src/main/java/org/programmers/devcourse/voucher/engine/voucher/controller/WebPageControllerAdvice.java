package org.programmers.devcourse.voucher.engine.voucher.controller;

import lombok.extern.slf4j.Slf4j;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice(assignableTypes = {WebPageVoucherController.class})
public class WebPageControllerAdvice {

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ModelAndView handleVoucherException(VoucherException exception) {
    log.info("VoucherException", exception);
    var modelAndView = new ModelAndView();
    modelAndView.setViewName("web-error.html");
    modelAndView.addObject("statusCode", HttpStatus.BAD_REQUEST.toString());
    return modelAndView;
  }
}
