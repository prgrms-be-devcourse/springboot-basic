package org.prgrms.kdt.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleException(NoHandlerFoundException exception, Model model) {
        logger.error(exception.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INNER_SERVER_ERROR, exception.getMessage());
        model.addAttribute("errorResponse", errorResponse);
        return "error/404";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleException(RuntimeException exception, Model model) {
        logger.error(exception.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INNER_SERVER_ERROR, exception.getMessage());
        model.addAttribute("errorResponse", errorResponse);
        return "error/500";
    }

}
