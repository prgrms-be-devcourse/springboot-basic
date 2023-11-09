package org.programmers.springorder.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.programmers.springorder.voucher.dto.Response;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Profile("default")
@RestControllerAdvice
public class RestExceptionHandler {


    @ExceptionHandler(VoucherException.class)
    public ResponseEntity<?> applicationHandler(VoucherException e){
        return ResponseEntity.status(
                e.getErrorCode().getHttpStatus())
                .body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> applicationHandler(RuntimeException e){
        return ResponseEntity.status(ErrorCode.SERVER_ERROR.getHttpStatus())
                .body(Response.error(ErrorCode.SERVER_ERROR.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodValidException(MethodArgumentNotValidException e, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorCode.INVALID_INPUT.getMessage());
    }
}
