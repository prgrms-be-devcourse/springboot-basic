package com.example.springbootbasic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewMainController {

    @GetMapping("/v1/home")
    public String mainPage() {
        return "main";
    }
}
