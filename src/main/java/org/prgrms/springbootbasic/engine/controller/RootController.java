package org.prgrms.springbootbasic.engine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    public RootController() {
    }

    @GetMapping("/")
    public String showHome() {
        return "views/home";
    }
}
