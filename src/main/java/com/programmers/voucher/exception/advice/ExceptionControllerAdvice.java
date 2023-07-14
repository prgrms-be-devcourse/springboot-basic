package com.programmers.voucher.exception.advice;

import com.programmers.voucher.constant.BaseResponse;
import com.programmers.voucher.exception.BadRequestException;
import com.programmers.voucher.exception.ConflictException;
import com.programmers.voucher.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            BadRequestException.class,
            IllegalArgumentException.class,
            HttpMessageNotReadableException.class
    })
    public BaseResponse<Object> handleBadRequestException(Exception exception) {
        log.error("[BadRequestException] = ", exception);
        return BaseResponse.error(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("[MethodArgumentNotValidException] = ", exception);

        FieldError error = exception.getBindingResult().getFieldError();
        return BaseResponse.error(HttpStatus.BAD_REQUEST.value(), error == null ? "" : error.getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public BaseResponse<Object> handleNotFoundException(NotFoundException exception) {
        log.error("[NotFoundException] = ", exception);
        return BaseResponse.error(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public BaseResponse<Object> handleConflictException(ConflictException exception) {
        log.error("[ConflictException] = ", exception);
        return BaseResponse.error(HttpStatus.CONFLICT.value(), exception.getMessage());
    }
}
