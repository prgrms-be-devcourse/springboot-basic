package com.weeklyMission.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler
    public String exceptionPage(Exception e, Model model){
        model.addAttribute("errorMessage", e.getMessage());
        return "error";
    }

}
