package com.prgrms.common.exception.handler;

import com.prgrms.common.codes.HttpErrorCode;
import com.prgrms.common.response.ErrorResponse;
import java.io.IOException;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final HttpStatus HTTP_STATUS_OK = HttpStatus.OK;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            stringBuilder.append(fieldError.getField()).append(":");
            stringBuilder.append(fieldError.getDefaultMessage());
            stringBuilder.append(", ");
        }
        final ErrorResponse response = ErrorResponse.of(HttpErrorCode.NOT_VALID_ERROR, String.valueOf(stringBuilder));
        return ResponseEntity.status(HTTP_STATUS_OK)
                .body(response);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        final ErrorResponse response = ErrorResponse.of(HttpErrorCode.REQUEST_BODY_MISSING_ERROR, ex.getMessage());
        return ResponseEntity.status(HTTP_STATUS_OK)
                .body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException", ex);
        final ErrorResponse response = ErrorResponse.of(HttpErrorCode.REQUEST_BODY_MISSING_ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ErrorResponse> handleMissingRequestHeaderExceptionException(
            MissingServletRequestParameterException ex) {
        log.error("handleMissingServletRequestParameterException", ex);
        final ErrorResponse response = ErrorResponse.of(HttpErrorCode.MISSING_REQUEST_PARAMETER_ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("handleIllegalArgumentException", ex);
        final ErrorResponse response = ErrorResponse.of(HttpErrorCode.INVALID_TYPE_VALUE, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    protected ResponseEntity<ErrorResponse> handleBadRequestException(HttpClientErrorException e) {
        log.error("HttpClientErrorException.BadRequest", e);
        final ErrorResponse response = ErrorResponse.of(HttpErrorCode.BAD_REQUEST_ERROR, e.getMessage());
        return ResponseEntity.status(HTTP_STATUS_OK)
                .body(response);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNoHandlerFoundExceptionException(NoHandlerFoundException e) {
        log.error("handleNoHandlerFoundExceptionException", e);
        final ErrorResponse response = ErrorResponse.of(HttpErrorCode.NOT_FOUND_ERROR, e.getMessage());
        return ResponseEntity.status(HTTP_STATUS_OK)
                .body(response);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException e) {
        log.error("handleNullPointerException", e);
        final ErrorResponse response = ErrorResponse.of(HttpErrorCode.NULL_POINT_ERROR, e.getMessage());
        return ResponseEntity.status(HTTP_STATUS_OK)
                .body(response);
    }

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<ErrorResponse> handleIOException(IOException ex) {
        log.error("handleIOException", ex);
        final ErrorResponse response = ErrorResponse.of(HttpErrorCode.IO_ERROR, ex.getMessage());
        return ResponseEntity.status(HTTP_STATUS_OK)
                .body(response);
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<ErrorResponse> handleNoSuchElementException (NoSuchElementException e) {
        log.error("handleNullPointerException", e);
        final ErrorResponse response = ErrorResponse.of(HttpErrorCode.NULL_POINT_ERROR, e.getMessage());
        return ResponseEntity.status(HTTP_STATUS_OK)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    protected final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        log.error("Exception", ex);
        final ErrorResponse response = ErrorResponse.of(HttpErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.status(HTTP_STATUS_OK)
                .body(response);
    }

}


