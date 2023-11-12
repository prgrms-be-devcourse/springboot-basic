package com.zerozae.voucher.common.advisor;

import com.zerozae.voucher.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class ApiExceptionAdvisor extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
        log.warn("Error Message = {}", errorMessage);
        ErrorResponse errorResponse = new ErrorResponse(status.value(), status.toString().substring(4) ,errorMessage);
        return ErrorResponse.toResponseEntity(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGlobalException(Exception e) {
        HttpStatus status = INTERNAL_SERVER_ERROR;
        log.error("Error Message = {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(status.value(), status.name(), e.getMessage());
        return ErrorResponse.toResponseEntity(errorResponse);
    }
}
