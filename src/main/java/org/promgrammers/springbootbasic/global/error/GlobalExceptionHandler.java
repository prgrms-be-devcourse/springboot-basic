package org.promgrammers.springbootbasic.global.error;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.promgrammers.springbootbasic.global.error.exception.BusinessException;
import org.promgrammers.springbootbasic.global.error.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.promgrammers.springbootbasic.global.error.exception.ErrorCode.INVALID_INPUT_VALUE;
import static org.promgrammers.springbootbasic.global.error.exception.ErrorCode.OUT_OF_FORMAT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessException(BusinessException e) {
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(INVALID_INPUT_VALUE.getStatus())
                .errorMsg(INVALID_INPUT_VALUE.getMessage())
                .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            errorResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> invalidFormatException(InvalidFormatException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(OUT_OF_FORMAT.getStatus())
                .errorMsg(OUT_OF_FORMAT.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }
}
