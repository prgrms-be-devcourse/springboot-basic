package org.prgrms.orderApp.error;

import org.prgrms.orderApp.restController.VoucherRestApiController;
import org.prgrms.orderApp.voucher.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import static org.prgrms.orderApp.error.RestApiErrorCode.*;

@RestControllerAdvice(basePackageClasses = {VoucherRestApiController.class, VoucherService.class})
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<ErrorResponse> illegalArgumentException() {
        return ErrorResponse.generateResponse(IllegalArgumentException);
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    protected ResponseEntity<ErrorResponse> noSuchElementException() {
        return ErrorResponse.generateResponse(NoSuchElementException);
    }

    @ExceptionHandler(value = {DateTimeParseException.class})
    protected ResponseEntity<ErrorResponse> dateTimeParseException() {
        return ErrorResponse.generateResponse(DateTimeParseException);
    }

    @ExceptionHandler(value = {CustomRuntimeException.class})
    protected ResponseEntity<ErrorResponse> testException(CustomRuntimeException e) {
        return ErrorResponse.generateResponse(e.getErrorCode());
    }


}
