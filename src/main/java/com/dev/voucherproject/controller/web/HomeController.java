package com.dev.voucherproject.controller.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/voucher")
    public String vouchersApplicationHome() {
        return "vouchers/home";
    }
}
