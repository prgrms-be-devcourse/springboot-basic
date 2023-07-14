package org.weekly.weekly.api.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.weekly.weekly.api.dto.ErrorResponse;
import org.weekly.weekly.customer.exception.CustomerException;
import org.weekly.weekly.util.ExceptionCode;
import org.weekly.weekly.voucher.exception.VoucherException;

@RestControllerAdvice
public class ApiAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler({VoucherException.class})
    public ResponseEntity handleVoucherException(VoucherException exception) {
        return handleExceptionInternal(exception.getExceptionCode());
    }

    @ExceptionHandler({CustomerException.class})
    public ResponseEntity handleCustomerException(CustomerException customerException) {
        return handleExceptionInternal(customerException.getExceptionCode());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity handleGlobalExcpetion(RuntimeException runtimeException) {
        return handleExceptionInternal(ExceptionCode.NOT_ACCESS, runtimeException.getMessage());
    }

    private ResponseEntity handleExceptionInternal(ExceptionCode exceptionCode) {
        return ResponseEntity.status(exceptionCode.getHttpStatus())
                .body(errorResponse(exceptionCode.name(), exceptionCode.getMessage()));
    }

    private ResponseEntity handleExceptionInternal(ExceptionCode exceptionCode, String message) {
        return ResponseEntity.status(exceptionCode.getHttpStatus())
                .body(errorResponse(exceptionCode.name(), message));
    }

    private ErrorResponse errorResponse(String code, String message) {
        return new ErrorResponse(code, message);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }
}
