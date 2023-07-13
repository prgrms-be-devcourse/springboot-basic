package com.programmers.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        );
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        );
        return new ResponseEntity<>(apiException, httpStatus);
    }
}
