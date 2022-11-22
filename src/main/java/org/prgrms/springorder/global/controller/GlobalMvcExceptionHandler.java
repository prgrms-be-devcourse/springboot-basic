package org.prgrms.springorder.global.controller;

import javax.servlet.http.HttpServletRequest;
import org.prgrms.springorder.global.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalMvcExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalMvcExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public String entityNotFoundExceptionHandling(EntityNotFoundException e,
        HttpServletRequest request, Model model) {

        logger.debug("exceptionName : {}, message {}", e.getClass().getSimpleName(),
            e.getMessage());

        int statusCode = HttpStatus.NOT_FOUND.value();

        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), statusCode,
            request.getRequestURI());
        model.addAttribute("errorResponse", errorResponse);
        return "error";
    }

    @ExceptionHandler(NullPointerException.class)
    public String nullPointExceptionHandling(NullPointerException e, HttpServletRequest request,
        Model model) {

        logger.debug("exceptionName : {}, message {}", e.getClass().getSimpleName(),
            e.getMessage());

        int statusCode = HttpStatus.BAD_REQUEST.value();

        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), statusCode,
            request.getRequestURI());
        model.addAttribute("errorResponse", errorResponse);

        return "error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String IllegalArgumentExceptionHandling(IllegalArgumentException e,
        HttpServletRequest request,
        Model model) {

        logger.debug("exceptionName : {}, message {}", e.getClass().getSimpleName(),
            e.getMessage());

        int statusCode = HttpStatus.BAD_REQUEST.value();

        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), statusCode,
            request.getRequestURI());
        model.addAttribute("errorResponse", errorResponse);
        return "error";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String IllegalStateExceptionHandling(IllegalStateException e,
        HttpServletRequest request,
        Model model) {

        logger.debug("exceptionName : {}, message {}", e.getClass().getSimpleName(),
            e.getMessage());

        int statusCode = HttpStatus.BAD_REQUEST.value();

        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), statusCode,
            request.getRequestURI());
        model.addAttribute("errorResponse", errorResponse);
        return "error";
    }

}
