package com.programmers.voucher.global;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GlobalWebController {
    @GetMapping("/error-page")
    public String errorPage() {
        return "errorPage";
    }
}
