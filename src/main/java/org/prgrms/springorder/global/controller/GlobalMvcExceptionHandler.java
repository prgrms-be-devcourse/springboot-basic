package org.prgrms.springorder.global.controller;

import javax.servlet.http.HttpServletRequest;
import org.prgrms.springorder.domain.customer.view.CustomerViewController;
import org.prgrms.springorder.domain.voucher.view.VoucherViewController;
import org.prgrms.springorder.global.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {CustomerViewController.class, VoucherViewController.class})
@Order(Ordered.LOWEST_PRECEDENCE)
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

    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class})
    public String nullPointExceptionHandling(RuntimeException e, HttpServletRequest request,
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
