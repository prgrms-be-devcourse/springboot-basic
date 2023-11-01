package org.prgrms.vouchermanager.handler;

import org.prgrms.vouchermanager.common.ApiResponse;
import org.prgrms.vouchermanager.exception.NotExistEmailException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ApiResponse errorHandler(RuntimeException e){
        return ApiResponse.error(e.getMessage());
    }
}
