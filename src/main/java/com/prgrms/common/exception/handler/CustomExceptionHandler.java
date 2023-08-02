package com.prgrms.common.exception.handler;

import com.prgrms.common.codes.HttpErrorCode;
import com.prgrms.common.exception.InsertException;
import com.prgrms.common.exception.UpdateException;
import com.prgrms.common.response.ErrorResponse;
import com.prgrms.voucher.exception.AmountLimitException;
import com.prgrms.common.exception.NegativeArgumentException;
import com.prgrms.voucher.exception.PercentLimitException;
import com.prgrms.common.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUpdateException(UpdateException e) {
        log.error("Handle NotUpdateException :", e);
        final ErrorResponse response = ErrorResponse.of(HttpErrorCode.UPDATE_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInsertException(InsertException e) {
        log.error("Handle NotUpdateException :", e);
        final ErrorResponse response = ErrorResponse.of(HttpErrorCode.INSERT_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(response);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleDiscountAmountLimitException(
            AmountLimitException e) {
        log.error("Handle AmountLimitException :", e);
        final ErrorResponse response = ErrorResponse.of(HttpErrorCode.DISCOUNT_AMOUNT_LIMIT_ERROR,
                e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleDiscountPercentLimitException(
            PercentLimitException e) {
        log.error("Handle PercentLimitException :", e);
        final ErrorResponse response = ErrorResponse.of(HttpErrorCode.DISCOUNT_PERCENT_LIMIT_ERROR,
                e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handelNegativeException(NegativeArgumentException e) {
        log.error("Handle NegativeArgumentException :", e);
        final ErrorResponse response = ErrorResponse.of(HttpErrorCode.NEGATIVE_ARGUMENT_ERROR,
                e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        log.error("Handle UserNotFoundException :", e);
        final ErrorResponse response = ErrorResponse.of(HttpErrorCode.USER_NOT_FOUND_ERROR,
                e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

}
