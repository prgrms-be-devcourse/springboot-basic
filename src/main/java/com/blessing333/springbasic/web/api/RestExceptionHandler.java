package com.blessing333.springbasic.web.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice("com.blessing333.springbasic.web.api")
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResult> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("[RestExceptionHandler]", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResult(e.getMessage()));
    }
}
