package com.dojinyou.devcourse.voucherapplication.common.controlleradvice;

import com.dojinyou.devcourse.voucherapplication.common.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler
    public ResponseEntity notFoundExceptionHandler(NotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity illegalArgumentsHandler(IllegalArgumentException e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
