package org.prgrms.springbasic.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.controller.api.response.ErrorResponse;
import org.prgrms.springbasic.utils.exception.NoDatabaseChangeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

import static java.time.LocalDateTime.now;
import static org.prgrms.springbasic.controller.api.response.ErrorResponse.responseOf;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class MyRestAdvice {

    @ExceptionHandler(value = {NoSuchElementException.class, NoDatabaseChangeException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> badRequest(HttpServletRequest request, RuntimeException exception) {
        log.error("Got invalid value: {}, path: {}", exception.getCause().getMessage(), request.getRequestURI());

        return ResponseEntity.badRequest()
                .body(responseOf(
                        now(),
                        BAD_REQUEST.value(),
                        request.getRequestURI(),
                        exception
                        )
                );
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> catchNotFound(HttpServletRequest request, Exception exception) {
        log.error("Got unexpected error: {}, path: {}", exception.getCause().getMessage(), request.getRequestURI());

        return ResponseEntity.badRequest()
                .body(responseOf(
                        now(),
                        INTERNAL_SERVER_ERROR.value(),
                        request.getRequestURI(),
                        exception
                        )
                );
    }
}
