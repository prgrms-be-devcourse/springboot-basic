package com.programmers.commandline.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class GlobalController {
    @GetMapping()
    public String index() {
        return "index";
    }
}
