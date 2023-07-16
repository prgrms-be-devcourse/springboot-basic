package com.programmers.application.controller;

import com.programmers.application.exception.NotFoundResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice(basePackages = "com.programmers.application.controller.api")
public class ApiExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(ErrorResult.of(HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResult.of(HttpStatus.FORBIDDEN.getReasonPhrase(), e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResult.of(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleNotFoundResourceException(NotFoundResourceException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResult.of(HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage()));
    }
}
