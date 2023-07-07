package kr.co.springbootweeklymission.common.error.handler;

import kr.co.springbootweeklymission.common.error.exception.NotFoundException;
import kr.co.springbootweeklymission.common.response.ResponseError;
import kr.co.springbootweeklymission.common.response.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ResponseError> handleNotFoundException(NotFoundException e) {
        log.warn("======= Handle NotFoundException =======", e);
        ResponseError responseError = ResponseError.builder()
                .exception(e.getMessage())
                .message(ResponseStatus.FAIL_NOT_FOUND_VOUCHER.getMessage())
                .httpStatus(ResponseStatus.FAIL_NOT_FOUND_VOUCHER.getHttpStatus())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(responseError);
    }
}
