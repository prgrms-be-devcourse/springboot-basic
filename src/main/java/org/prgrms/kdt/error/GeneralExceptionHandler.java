package org.prgrms.kdt.error;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.kdt.controller.ApiResult;
import org.prgrms.kdt.error.ServiceRuntimeException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.prgrms.kdt.controller.ApiResult.ERROR;

@Slf4j
@ControllerAdvice
public class GeneralExceptionHandler {

    private ResponseEntity<ApiResult<?>> newResponse(Throwable throwable, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(ERROR(throwable, status), headers, status);
    }

    //500 - Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResult<?>> handleInternalServerError(Throwable throwable) {
        log.error("Internal Server Error: {}", throwable.getMessage());
        return newResponse(throwable, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //503 - Service Unavailable
    @ExceptionHandler(ServiceRuntimeException.class)
    public ResponseEntity<ApiResult<?>> handlerServiceUnavailableException(ServiceRuntimeException throwable) {
        log.error("[*] Service Unavailable Error: {}", throwable.getMessage());
        return newResponse(throwable, HttpStatus.SERVICE_UNAVAILABLE);
    }

    //400 - Bad Request
    @ExceptionHandler({IllegalArgumentException.class,
            IllegalStateException.class,
            TypeMismatchException.class,
            MissingServletRequestParameterException.class,
            JSONException.class})
    public ResponseEntity<ApiResult<?>> handleBadRequestException(Throwable throwable) {
        log.error("[*] Bad Request: {}", throwable.getMessage());
        return newResponse(throwable, HttpStatus.BAD_REQUEST);
    }

    //405 - Method Not Allowed
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResult<?>> handleMethodNotAllowed(HttpRequestMethodNotSupportedException throwable) {
        log.warn("Method Not Allowed: {}", throwable.getMethod());
        return newResponse(throwable, HttpStatus.METHOD_NOT_ALLOWED);
    }

    //415 - Unsupported Data Type
    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<ApiResult<?>> handleUnsupportedMediaTypeException(HttpMediaTypeException throwable) {
        log.warn("[*] Unsupported Media Type: {}", throwable.getMessage());
        return newResponse(throwable, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

}
