package org.programmers.springboot.basic.util.handler;

import org.programmers.springboot.basic.domain.customer.exception.CustomerNotFoundException;
import org.programmers.springboot.basic.domain.customer.exception.DuplicateBlackCustomerException;
import org.programmers.springboot.basic.domain.customer.exception.DuplicateEmailException;
import org.programmers.springboot.basic.domain.voucher.exception.DuplicateVoucherException;
import org.programmers.springboot.basic.domain.voucher.exception.IllegalDiscountException;
import org.programmers.springboot.basic.domain.voucher.exception.VoucherNotFoundException;
import org.programmers.springboot.basic.domain.wallet.exception.DuplicateWalletException;
import org.programmers.springboot.basic.util.response.ErrCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.programmers.springboot.basic.util.response.ErrResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrResponse response = new ErrResponse(ErrCode.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrResponse> handleNumberFormatException(NumberFormatException e) {
        ErrResponse response = new ErrResponse(ErrCode.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalDiscountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrResponse> handleIllegalDiscountException(IllegalDiscountException e) {
        ErrResponse response = new ErrResponse(ErrCode.INPUT_INVALID_VALUE, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateVoucherException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrResponse> handleDuplicateVoucherException(DuplicateVoucherException e) {
        ErrResponse response = new ErrResponse(ErrCode.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrResponse> handleDuplicateEmailException(DuplicateEmailException e) {
        ErrResponse response = new ErrResponse(ErrCode.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateBlackCustomerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrResponse> handleDuplicateBlackCustomerException(DuplicateBlackCustomerException e) {
        ErrResponse response = new ErrResponse(ErrCode.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateWalletException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrResponse> handleDuplicateWalletException(DuplicateWalletException e) {
        ErrResponse response = new ErrResponse(ErrCode.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VoucherNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrResponse> handleVoucherNotFoundException(VoucherNotFoundException e) {
        ErrResponse response = new ErrResponse(ErrCode.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrResponse> handleCustomerNotFoundException(CustomerNotFoundException e) {
        ErrResponse response = new ErrResponse(ErrCode.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
