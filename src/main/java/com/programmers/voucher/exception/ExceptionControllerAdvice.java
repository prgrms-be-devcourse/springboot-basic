package com.programmers.voucher.exception;

import com.programmers.voucher.constant.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler({
            BadRequestException.class,
            IllegalArgumentException.class
    })
    public BaseResponse<Object> handleBadRequestException(IllegalArgumentException exception) {
        log.error("[BadRequestException] = ", exception);
        return BaseResponse.error(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public BaseResponse<Object> handleNotFoundException(NotFoundException exception) {
        log.error("[NotFoundException] = ", exception);
        return BaseResponse.error(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public BaseResponse<Object> handleConflictException(ConflictException exception) {
        log.error("[ConflictException] = ", exception);
        return BaseResponse.error(HttpStatus.CONFLICT.value(), exception.getMessage());
    }
}
