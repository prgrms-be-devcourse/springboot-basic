package com.programmers.springbootbasic.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NoMappingThymeExceptionHandler implements ErrorController {
    @RequestMapping("/error")
    public String handleError() {
        return "defaultError";
    }
}
