package com.programmers.assignment.voucher.common.exceptions;

import com.programmers.assignment.voucher.common.response.CommonResponse;
import com.programmers.assignment.voucher.common.response.ResponseCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public CommonResponse<?> handleNoSuchElementException(NoSuchElementException exception) {
        return new CommonResponse<>(ResponseCode.NOT_FOUND_CUSTOMER);
    }

    @ExceptionHandler(InputMismatchException.class)
    public CommonResponse<?> handleInputMismatchException(InputMismatchException exception) {
        return new CommonResponse<>(ResponseCode.NOT_FOUND_VOUCHER);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public CommonResponse<?> handleIllegalArgumentException(IllegalArgumentException exception) {
        return new CommonResponse<>(ResponseCode.INVALID_DISCOUNT_VALUE);
    }

    @ExceptionHandler(NoSuchFieldException.class)
    public CommonResponse<?> handleNoSuchFieldException(NoSuchFieldException exception) {
        return new CommonResponse<>(ResponseCode.NOT_AVAILABLE_VOUCHER);
    }
}
