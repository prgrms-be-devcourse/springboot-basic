package org.prgrms.kdt.global;

import org.prgrms.kdt.global.exception.EntityNotFoundException;
import org.prgrms.kdt.global.exception.InvalidInputException;
import org.prgrms.kdt.voucher.exception.InvalidDiscountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiExceptionHandler{
    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(HttpServletRequest request, BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            stringBuilder.append(fieldError.getField()).append(":");
            stringBuilder.append(fieldError.getDefaultMessage());
            stringBuilder.append(", ");
        }
        int statusCode = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getErrorResponse(statusCode, stringBuilder.toString(), request.getRequestURI()));
    }

    @ExceptionHandler({NullPointerException.class, InvalidInputException.class, InvalidDiscountException.class, MissingRequestHeaderException.class, HttpMessageNotReadableException.class,
            HttpClientErrorException.BadRequest.class, NoHandlerFoundException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestException(HttpServletRequest request, Exception e) {
        int statusCode = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getErrorResponse(statusCode, e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(HttpServletRequest request, EntityNotFoundException e){
        int statusCode = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getErrorResponse(statusCode, e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request, Exception e) {
        logger.error("Exception: ", e);
        int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getErrorResponse(statusCode, e.getMessage(), request.getRequestURI()));
    }

    private static ErrorResponse getErrorResponse(int status, String masesage, String requestURI) {
        return new ErrorResponse(status, masesage, requestURI);
    }
}
