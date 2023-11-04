package org.programmers.springboot.basic.web;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Profile("default")
@Controller
public class ApplicationController {

    @GetMapping
    public String home() {
        return "home";
    }
}
