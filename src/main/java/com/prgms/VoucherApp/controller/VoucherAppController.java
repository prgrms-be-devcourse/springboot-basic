package com.prgms.VoucherApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VoucherAppController {

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }
}
