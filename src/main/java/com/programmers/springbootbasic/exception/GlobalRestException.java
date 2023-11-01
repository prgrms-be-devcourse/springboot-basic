package com.programmers.springbootbasic.exception;

import com.programmers.springbootbasic.exception.exceptionClass.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalRestException {

    @ExceptionHandler({
        CustomException.class,
    })
    public ResponseEntity<String> handleUserException(CustomException ex) {
        return ResponseEntity
            .status(ex.getErrorCode().getHttpStatus())
            .body(ex.getMessage());
    }

    @ExceptionHandler({
        Exception.class,
    })
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity
            .status(500)
            .body(ex.getMessage());
    }

}
