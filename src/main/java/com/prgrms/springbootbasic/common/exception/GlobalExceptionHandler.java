package com.prgrms.springbootbasic.common.exception;

import com.prgrms.springbootbasic.common.dto.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(InvalidVoucherTypeException.class)
    public ResponseEntity<Object> handleInvalidVoucherTypeException(InvalidVoucherTypeException e){
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode, e.getMessage());
    }

    @ExceptionHandler(AmountOutOfBoundException.class)
    public ResponseEntity<Object> handleAmountOutOfBoundException(AmountOutOfBoundException e){
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode, e.getMessage());
    }

    @ExceptionHandler(DataModifyingException.class)
    public ResponseEntity<Object> handleDataModifyingException(DataModifyingException e){
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode, e.getMessage());
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, message));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(status)
                .body(makeErrorResponse(CommonErrorCode.INVALID_NUMBER_FORMAT, ex.getMessage()));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message) {
        return new ErrorResponse(errorCode.name(), message);
    }
}
