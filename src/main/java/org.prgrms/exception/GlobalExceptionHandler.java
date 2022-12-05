package org.prgrms.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundVoucherException.class)
    public ResponseEntity<ErrorResponse> handleException(NotFoundVoucherException e) {
        return new ResponseEntity<>(ErrorResponse.of(e.getErrorCode()), e.getErrorCode().getStatus());
    }

}
