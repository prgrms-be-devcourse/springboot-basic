package com.programmers.assignment.voucher.common.exceptions;

import com.programmers.assignment.voucher.common.response.CommonResponse;
import com.programmers.assignment.voucher.common.response.ResponseMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public CommonResponse<?> handleNoSuchElementException(NoSuchElementException exception) {
        return new CommonResponse<>(ResponseMessage.NOT_FOUND);
    }

    @ExceptionHandler(InputMismatchException.class)
    public CommonResponse<?> handleInputMismatchException(InputMismatchException exception) {
        return new CommonResponse<>(ResponseMessage.INVALID_DISCOUNT_VALUE);
    }

    @ExceptionHandler(NoSuchFieldException.class)
    public CommonResponse<?> handleNoSuchFieldException(NoSuchFieldException exception) {
        return new CommonResponse<>(ResponseMessage.NOT_AVAILABLE_VOUCHER);
    }
}
