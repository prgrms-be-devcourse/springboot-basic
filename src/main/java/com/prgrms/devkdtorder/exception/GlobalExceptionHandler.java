package com.prgrms.devkdtorder.exception;

import com.prgrms.devkdtorder.util.ApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import static com.prgrms.devkdtorder.util.ApiUtils.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private ResponseEntity<ApiResponse<?>> newResponseEntity(Throwable throwable, HttpStatus status) {
        return newResponseEntity(throwable.getMessage(), status);
    }

    private ResponseEntity<ApiResponse<?>> newResponseEntity(String message, HttpStatus status) {
        return new ResponseEntity<>(ApiUtils.error(message, status), status);
    }


    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class
    })
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        log.debug("Bad request exception occurred: {}", e.getMessage(), e);
        return newResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<?> handleException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return newResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
