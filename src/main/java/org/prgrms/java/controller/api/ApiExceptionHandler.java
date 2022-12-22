package org.prgrms.java.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.java.exception.badrequest.BadRequestException;
import org.prgrms.java.exception.notfound.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<String> handleException(BadRequestException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<String> handleException(NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception e) {
        log.error("알 수 없는 에러가 발생했습니다. ", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }
}
