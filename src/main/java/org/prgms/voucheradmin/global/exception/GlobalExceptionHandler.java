package org.prgms.voucheradmin.global.exception;

import org.prgms.voucheradmin.global.exception.customexception.VoucherNotFoundException;
import org.prgms.voucheradmin.global.exception.customexception.WrongInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponseDto> handleBindException(BindException e) {
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.of(HttpStatus.BAD_REQUEST.value(), e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponseDto);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.of(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        logger.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponseDto);
    }

    @ExceptionHandler(WrongInputException.class)
    protected ResponseEntity<ErrorResponseDto> handleWrongInputException(WrongInputException e) {
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.of(HttpStatus.BAD_REQUEST.value(), e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponseDto);
    }

    @ExceptionHandler(VoucherNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleVoucherNotFoundException(VoucherNotFoundException e) {
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.of(HttpStatus.NOT_FOUND.value(), e.getMessage());
        logger.error(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponseDto);
    }
}
