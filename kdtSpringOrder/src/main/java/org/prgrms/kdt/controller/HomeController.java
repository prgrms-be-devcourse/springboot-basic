package org.prgrms.kdt.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "home";
    }
}
