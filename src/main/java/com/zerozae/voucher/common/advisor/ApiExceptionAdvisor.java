package com.zerozae.voucher.common.advisor;

import com.zerozae.voucher.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
        log.warn("Error Message = {}", errorMessage);
        return getFailureResponse(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public Response exceptionMessage(Exception e){
        log.error("Error Message ={}", e.getMessage());
        return getFailureResponse(e.getMessage());
    }

    private Response getFailureResponse(String message) {
        return Response.failure(message);
    }
}
