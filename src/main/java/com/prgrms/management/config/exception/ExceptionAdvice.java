package com.prgrms.management.config.exception;

import com.prgrms.management.config.common.CommonResult;
import com.prgrms.management.config.common.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

import static com.prgrms.management.config.ErrorMessageType.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionAdvice {
    private final ResponseService responseService;

    @ExceptionHandler(NotExistException.class)
    public ResponseEntity<CommonResult> notExistException(NotExistException e) {
        CommonResult commonResult = responseService.getFailResult(NOT_EXIST_EXCEPTION);
        return new ResponseEntity<>(commonResult, NOT_FOUND);
    }

    @ExceptionHandler(NotSavedException.class)
    public ResponseEntity<CommonResult> notSavedException(NotSavedException e) {
        CommonResult commonResult = responseService.getFailResult(NOT_SAVED_EXCEPTION);
        return new ResponseEntity<>(commonResult, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<CommonResult> unsupportedOperationException(UnsupportedOperationException e) {
        CommonResult commonResult = responseService.getFailResult(UN_SUPPORTED_OPERATION_EXCEPTION);
        return new ResponseEntity<>(commonResult, SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<CommonResult> illegalStateException(IllegalStateException e) {
        CommonResult commonResult = responseService.getFailResult(NOT_EXECUTE_QUERY);
        return new ResponseEntity<>(commonResult, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<CommonResult> duplicatedEmailException(DuplicatedEmailException e) {
        CommonResult commonResult = responseService.getFailResult(DUPLICATE_CUSTOMER_EMAIL);
        return new ResponseEntity<>(commonResult, CONFLICT);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<CommonResult> noSuchElementException(NoSuchElementException e) {
        CommonResult commonResult = responseService.getFailResult(NOT_EXIST_TYPE);
        return new ResponseEntity<>(commonResult, NOT_ACCEPTABLE);
    }
}