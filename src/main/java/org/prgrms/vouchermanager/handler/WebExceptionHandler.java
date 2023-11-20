package org.prgrms.vouchermanager.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public String errorHandler(RuntimeException e, Model model){
        model.addAttribute("message", e.getMessage());
        return "error";
    }

}
