package com.example.weeklymission3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VoucherController {

    @GetMapping("/")
    public String mainPage() {
        return "main";
    }

}
