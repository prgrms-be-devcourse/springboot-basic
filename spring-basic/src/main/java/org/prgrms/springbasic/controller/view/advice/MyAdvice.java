package org.prgrms.springbasic.controller.view.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(annotations = Controller.class)
public class MyAdvice {

    @ExceptionHandler(Exception.class)
    public String catchDatabaseException(Exception e, Model model) {
        log.error("Got exception: {}", e.getMessage());

        model.addAttribute("errorMessage", e.getMessage());

        return "/error";
    }

}
