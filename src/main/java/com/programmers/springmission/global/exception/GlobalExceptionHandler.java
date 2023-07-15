package com.programmers.springmission.global.exception;

import com.programmers.springmission.global.exception.response.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ExceptionResponse> invalidInputException(InvalidInputException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(400, e.getMessage());
        return ResponseEntity.ok(exceptionResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFoundException(NotFoundException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(400, e.getMessage());
        return ResponseEntity.ok(exceptionResponse);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ExceptionResponse> duplicateException(DuplicateException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(400, e.getMessage());
        return ResponseEntity.ok(exceptionResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> invalidRequestHandler(MethodArgumentNotValidException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(400, "잘못된 요청입니다.");
        return ResponseEntity.ok(exceptionResponse);
    }
}
