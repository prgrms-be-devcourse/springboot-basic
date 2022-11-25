package com.programmers.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiControllerAdvice {

    private final Logger log = LoggerFactory.getLogger(ApiControllerAdvice.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity runtimeExceptionHandler(RuntimeException e) {
        log.error("API Runtime exception", e.getMessage());

        Map body = Map.of(
                "error", HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "status", HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception e) {
        log.error("API Exception", e.getMessage());

        Map body = Map.of(
                "error", HttpStatus.NOT_FOUND.getReasonPhrase(),
                "status", HttpStatus.NOT_FOUND.value()
        );

        return new ResponseEntity(body, HttpStatus.NOT_FOUND);
    }
}
