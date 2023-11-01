package com.pgms.part1.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {

    private final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(VoucherApplicationException.class)
    public ModelAndView handelVoucherApplicationException(VoucherApplicationException e){
        log.warn("Error occurs {}", e.toString());

        ModelAndView mv = new ModelAndView("error");
        mv.addObject("message", e.getErrorCode().getMessage());
        mv.addObject("code", e.getErrorCode().getStatus());
        return mv;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handelCommonException(Exception e){
        log.error("Error occurs {}", e.toString());

        ModelAndView mv = new ModelAndView("error");
        mv.addObject("message", e.getMessage());
        return mv;
    }
}
