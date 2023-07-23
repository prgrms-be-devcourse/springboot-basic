package org.prgrms.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String getHomePage(Model model) {
        model.addAttribute("message", LocalDateTime.now());
        return "home";
    }
}
