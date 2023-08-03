package com.tangerine.voucher_system.application.global.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;

@RestControllerAdvice
public class VoucherSystemExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(VoucherSystemExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> internetServerErrorExceptionHandler(Exception e) {
        String errorMessage = "Internal Server Error";
        logger.error(errorMessage, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> runtimeErrorExceptionHandler(RuntimeException e) {
        String errorMessage = MessageFormat.format("Runtime error: {0}", e.getMessage());
        logger.error(errorMessage, e);
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<String> invalidDataExceptionHandler(InvalidDataException e) {
        String errorMessage = MessageFormat.format("Invalid Data: {0}", e.getMessage());
        logger.error(errorMessage, e);
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(SqlException.class)
    public ResponseEntity<String> sqlErrorExceptionHandler(SqlException e) {
        String errorMessage = MessageFormat.format("SQL Error: {0}", e.getMessage());
        logger.error(errorMessage, e);
        return ResponseEntity.badRequest().body(errorMessage);
    }
}
