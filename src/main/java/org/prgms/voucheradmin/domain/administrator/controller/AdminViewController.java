package org.prgms.voucheradmin.domain.administrator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminViewController {

    @GetMapping("/")
    public String viewMainPage() {
        return "views/index";
    }
}
