package com.programmers.springbootbasic.menu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuViewController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}