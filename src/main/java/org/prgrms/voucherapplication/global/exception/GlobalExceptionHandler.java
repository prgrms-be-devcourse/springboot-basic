package org.prgrms.voucherapplication.global.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<String> handledException(BusinessException e) {
        logger.warn(e.getMessage(), e);
        HttpStatus httpStatus = HttpStatus.valueOf(e.getStatus());
        return ResponseEntity.status(httpStatus).body(e.getMessage());
    }
}
