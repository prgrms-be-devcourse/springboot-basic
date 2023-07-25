package com.programmers.voucher.global.advice;

import com.programmers.voucher.global.ErrorResult;
import com.programmers.voucher.global.util.CommonErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice(annotations = RestController.class)
public class GlobalApiControllerAdvice {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalApiControllerAdvice.class);

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResult> noSuchElementExHandle(NoSuchElementException ex) {
        ErrorResult errorResult = new ErrorResult("404", ex.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResult> duplicateKeyExHandle(DuplicateKeyException ex) {
        ErrorResult errorResult = new ErrorResult("409", ex.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResult> illegalArgumentExHandle(IllegalArgumentException ex) {
        ErrorResult errorResult = new ErrorResult("400", ex.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> methodArgumentNotValidExHandle(MethodArgumentNotValidException ex) {
        String errorMessages = getBindingResultErrorMessages(ex);
        ErrorResult errorResult = new ErrorResult("400", errorMessages);
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    private String getBindingResultErrorMessages(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder();
        for (ObjectError allError : bindingResult.getAllErrors()) {
            sb.append(allError.getDefaultMessage()).append(",");
        }
        return sb.toString();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResult> runtimeExHandle(RuntimeException ex) {
        LOG.error(CommonErrorMessages.UNEXPECTED_EXCEPTION, ex);
        ErrorResult errorResult = new ErrorResult("500", CommonErrorMessages.UNEXPECTED_EXCEPTION);
        return new ResponseEntity<>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
