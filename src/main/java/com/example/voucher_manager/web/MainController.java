package com.example.voucher_manager.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String viewMainPage() {
        return "views/main";
    }
}
