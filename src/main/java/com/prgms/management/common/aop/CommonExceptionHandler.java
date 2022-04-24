package com.prgms.management.common.aop;

import com.prgms.management.common.dto.ErrorResponse;
import com.prgms.management.common.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(FindFailException.class)
    public ResponseEntity<ErrorResponse> handleFindFailException() {
        return ResponseEntity.notFound().build();
    }
    
    @ExceptionHandler(EmptyListException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeExceptions() {
        return ResponseEntity.noContent().build();
    }
    
    @ExceptionHandler(WrongRequestParamException.class)
    public ResponseEntity<ErrorResponse> handleWrongRequestParamException(WrongRequestParamException e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
    
    @ExceptionHandler(SaveFailException.class)
    public ResponseEntity<ErrorResponse> handleSaveFailException(SaveFailException e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }
    
    @ExceptionHandler(UpdateFailException.class)
    public ResponseEntity<ErrorResponse> handleUpdateFailException(UpdateFailException e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }
    
    @ExceptionHandler(DeleteFailException.class)
    public ResponseEntity<ErrorResponse> handleDeleteFailException(DeleteFailException e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }
}
