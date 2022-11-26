package org.prgrms.kdt.voucher.controller;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.kdt.exception.ErrorResult;
import org.prgrms.kdt.exception.voucher.VoucherServerException;
import org.prgrms.kdt.exception.voucher.VoucherUserException;
import org.prgrms.kdt.util.ControllerResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice(assignableTypes = VoucherRestController.class)
public class VoucherExceptionRestControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler()
    public ErrorResult userHandler(VoucherUserException userException) {
        log.error("[customerExceptionHandler] => {}", userException);

        return new ErrorResult(ControllerResult.USER_ERROR, userException.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult serverHandler(VoucherServerException serverException) {
        log.error("[customerExceptionHandler] => {}", serverException);

        return new ErrorResult(ControllerResult.SERVER_ERROR, serverException.getMessage());
    }

}
