package org.prgrms.kdt.global;

import org.prgrms.kdt.global.exception.EntityNotFoundException;
import org.prgrms.kdt.global.exception.InvalidInputException;
import org.prgrms.kdt.global.exception.NotUpdateException;
import org.prgrms.kdt.voucher.exception.InvalidDiscountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getErrorResponse(400, e));
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getErrorResponse(400, e));
    }

    @ExceptionHandler(NotUpdateException.class)
    public ResponseEntity<ErrorResponse> handleNotUpdateException(NotUpdateException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getErrorResponse(500, e));
    }

    @ExceptionHandler(InvalidDiscountException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDiscountException(InvalidDiscountException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getErrorResponse(400, e));
    }

    private static ErrorResponse getErrorResponse(int status, Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(status, e.getMessage());
        return errorResponse;
    }
}
