package org.weekly.weekly.global.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.weekly.weekly.customer.exception.CustomerException;
import org.weekly.weekly.voucher.exception.VoucherException;

@RestControllerAdvice(basePackages = {"org.weekly.weekly.api"})
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({VoucherException.class})
    public ResponseEntity<ErrorResponse> handleVoucherException(VoucherException exception) {
        return handleExceptionInternal(exception.getExceptionCode());
    }

    @ExceptionHandler({CustomerException.class})
    public ResponseEntity<ErrorResponse> handleCustomerException(CustomerException customerException) {
        return handleExceptionInternal(customerException.getExceptionCode());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleGlobalException(RuntimeException runtimeException) {
        return handleExceptionInternal(ExceptionCode.NOT_ACCESS, runtimeException.getMessage());
    }

    private ResponseEntity<ErrorResponse> handleExceptionInternal(ExceptionCode exceptionCode) {
        return ResponseEntity.status(exceptionCode.getHttpStatus())
                .body(errorResponse(exceptionCode.name(), exceptionCode.getMessage()));
    }

    private ResponseEntity<ErrorResponse> handleExceptionInternal(ExceptionCode exceptionCode, String message) {
        return ResponseEntity.status(exceptionCode.getHttpStatus())
                .body(errorResponse(exceptionCode.name(), message));
    }

    private ErrorResponse errorResponse(String code, String message) {
        return new ErrorResponse(code, message);
    }
}
