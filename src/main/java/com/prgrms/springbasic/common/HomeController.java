package com.prgrms.springbasic.common;

import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

    public String main() {
        return "/index";
    }
}
