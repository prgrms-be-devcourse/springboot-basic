package org.prgms.kdtspringvoucher.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceptionAdvise {

    private final Logger log = LoggerFactory.getLogger(GlobalControllerExceptionAdvise.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public Exception illegalArgException(IllegalArgumentException exception) {
        log.error("error message = {}", exception.getMessage());
        return exception;
    }


    @ExceptionHandler(Exception.class)
    public Exception AllException(Exception exception) {
        log.error("error message = {}", exception.getMessage());
        return exception;
    }
}
