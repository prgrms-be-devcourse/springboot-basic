package org.prgrms.springbasic.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.controller.api.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

import static java.time.LocalDateTime.now;
import static org.prgrms.springbasic.controller.api.ErrorCode.*;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class MyRestAdvice {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> catchInvalidValue(IllegalArgumentException e) {
        log.error("Got invalid value: {}", e.getCause().getMessage());

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ErrorResponse
                        .responseOf(
                                now(),
                                INVALID_TYPE_VALUE,
                                e.getCause().getClass().getSimpleName()
                        )
                );
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> catchBadRequest(NoSuchElementException e) {
        log.error("Got bad request: {}", e.getCause().getMessage());

        return ResponseEntity
                .status(NOT_FOUND)
                .body(ErrorResponse
                        .responseOf(
                                now(),
                                ENTITY_NOT_FOUND,
                                e.getCause().getClass().getSimpleName()
                        )
                );
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> catchNotFound(Exception e) {
        log.error("Got service unavailable: {}", e.getCause().getMessage());

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ErrorResponse
                        .responseOf(
                                now(),
                                SERVICE_NOT_SUPPORT,
                                e.getCause().getClass().getSimpleName()
                        )
                );
    }
}