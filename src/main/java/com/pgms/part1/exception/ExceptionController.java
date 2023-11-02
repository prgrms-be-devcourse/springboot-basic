package com.pgms.part1.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@ControllerAdvice
public class ExceptionController {

    private final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(VoucherApplicationException.class)
    public ModelAndView handelVoucherApplicationException(VoucherApplicationException e, HttpServletResponse response){
        log.warn("Error occurs {}", e.toString());

        ModelAndView mv = new ModelAndView("error");
        mv.addObject("message", e.getErrorCode().getMessage());
        mv.addObject("code", e.getErrorCode().getStatus());

        response.setStatus(e.getErrorCode().getStatus());

        return mv;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handelVoucherApplicationException(MethodArgumentNotValidException e, HttpServletResponse response){
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        StringBuilder msg = new StringBuilder();

        for(var fieldError : fieldErrors){
            msg.append(fieldError.getField()).append('\n');
            msg.append(fieldError.getDefaultMessage()).append('\n');
            msg.append(" invalid value : ").append(fieldError.getRejectedValue()).append('\n');
        }

        ModelAndView mv = new ModelAndView("error");
        mv.addObject("message", msg.toString());
        mv.addObject("code", HttpStatus.BAD_REQUEST.value());

        response.setStatus(HttpStatus.BAD_REQUEST.value());

        return mv;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handelCommonException(Exception e, HttpServletResponse response){
        log.error("Error occurs {}", e.toString());

        ModelAndView mv = new ModelAndView("error");
        mv.addObject("message", e.getMessage());

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return mv;
    }
}
