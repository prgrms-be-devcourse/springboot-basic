package com.programmers.springbootbasic.exception;

import com.programmers.springbootbasic.domain.voucher.presentation.VoucherRestController;
import com.programmers.springbootbasic.exception.exceptionClass.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = VoucherRestController.class)
public class GlobalRestExceptionHandler {

    @ExceptionHandler({
        CustomException.class,
    })
    public ResponseEntity<ErrorResponse> handleUserException(CustomException ex) {
        return ResponseEntity
            .status(ex.getErrorCode().getHttpStatus())
            .body(ErrorResponse.builder()
            .message(ex.getMessage())
            .status(ex.getErrorCode().getHttpStatus().value())
            .code(ex.getErrorCode().name())
            .build());
    }

    @ExceptionHandler(value = {
        BindException.class,
        MethodArgumentNotValidException.class
    })
    public ResponseEntity<ErrorResponse> validationError(BindException e) {
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(" 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]\n");
        }
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
            .message(builder.toString())
            .status(400)
            .code(ErrorCode.INVALID_INPUT.name())
            .build());
    }

    @ExceptionHandler({
        Exception.class,
    })
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity
            .internalServerError()
            .body(ex.getMessage());
    }

}
