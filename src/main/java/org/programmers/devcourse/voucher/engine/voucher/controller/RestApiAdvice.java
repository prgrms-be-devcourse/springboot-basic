package org.programmers.devcourse.voucher.engine.voucher.controller;

import java.util.Map;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {VoucherRestController.class})
public class RestApiAdvice {

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, String> handleVoucherException(VoucherException exception) {

    return Map.of("message", exception.getMessage());
  }
}
