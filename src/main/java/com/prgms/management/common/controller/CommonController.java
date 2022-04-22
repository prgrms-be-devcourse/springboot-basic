package com.prgms.management.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("menuType", "MAIN");
        return "main";
    }
}
