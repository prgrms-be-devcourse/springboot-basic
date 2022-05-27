package org.prgrms.kdt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @GetMapping("/")
    public String indexPage() {
        return "redirect:/vouchers";
    }
}
