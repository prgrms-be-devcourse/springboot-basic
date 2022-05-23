package org.prgrms.kdt.controller;

import org.prgrms.kdt.error.InputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.prgrms.kdt.controller.ApiResponse.error;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<ApiResponse<?>> newResponse(Throwable throwable, HttpStatus status) {
        return new ResponseEntity<>(error(throwable, status), status);
    }

    @ExceptionHandler({InputException.class, IllegalArgumentException.class})
    private ResponseEntity<?> handleBadRequestException(Exception e) {
        logger.warn("Bad request exception occurred: {}", e.getMessage(), e);
        return newResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<?> handleAllException(Exception e) {
        logger.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
