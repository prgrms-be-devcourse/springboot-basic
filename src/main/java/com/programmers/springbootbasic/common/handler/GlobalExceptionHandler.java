package com.programmers.springbootbasic.common.handler;

import com.programmers.springbootbasic.common.response.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult<String> defaultException(Exception e) {
        return CommonResult.getFailResult(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult<String> illegalArgumentException(IllegalArgumentException e) {
        return CommonResult.getFailResult(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult<String> runtimeException(RuntimeException e) {
        return CommonResult.getFailResult(e.getMessage());
    }
}
