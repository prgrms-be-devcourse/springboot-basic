package com.programmers.voucher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/voucher/")
public class VoucherWebController {

    @GetMapping("")
    public String main() {
        return "main";
    }
}
