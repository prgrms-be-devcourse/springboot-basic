package com.prgms.VoucherApp.domain.voucher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/voucher")
public class VoucherController {

    @GetMapping("/main")
    public String mainPage() {
        return "voucher/main";
    }
}
