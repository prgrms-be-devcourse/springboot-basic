package com.blessing333.springbasic.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice("com.blessing333.springbasic.web.controller")
@Slf4j
public class WebExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView serviceFailHandle(IllegalArgumentException e) {
        log.error("[exceptionHandle] ex", e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/error");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }
}
