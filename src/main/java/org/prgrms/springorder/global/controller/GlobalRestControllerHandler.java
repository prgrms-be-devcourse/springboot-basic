package org.prgrms.springorder.global.controller;

import javax.servlet.http.HttpServletRequest;
import org.prgrms.springorder.global.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalRestControllerHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalRestControllerHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFoundExceptionHandling(EntityNotFoundException e,
        HttpServletRequest request) {

        logger.debug("exceptionName : {}, message {}", e.getClass().getSimpleName(),
            e.getMessage());

        int statusCode = HttpStatus.NOT_FOUND.value();

        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), statusCode,
            request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class,
        IllegalStateException.class})
    public ResponseEntity<ErrorResponse> badRequestExceptionHandling(RuntimeException e,
        HttpServletRequest request,
        Model model) {

        logger.debug("exceptionName : {}, message {}", e.getClass().getSimpleName(),
            e.getMessage());

        int statusCode = HttpStatus.BAD_REQUEST.value();

        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), statusCode,
            request.getRequestURI());
        model.addAttribute("errorResponse", errorResponse);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
