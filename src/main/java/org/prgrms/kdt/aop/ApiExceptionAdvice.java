package org.prgrms.kdt.aop;

import org.prgrms.kdt.exception.BadRequestException;
import org.prgrms.kdt.exception.ErrorResponse;
import org.prgrms.kdt.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by yhh1056
 * Date: 2021/09/07 Time: 9:21 오후
 */
@RestControllerAdvice
public class ApiExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionAdvice.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(ResourceNotFoundException e) {
        logger.error("is invalid request : " +  e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.toString(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(BadRequestException e) {
        logger.error("is invalid request : " +  e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
