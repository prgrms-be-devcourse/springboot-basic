package org.prgrms.kdt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebManagementController {
    @GetMapping("")
    public String managementPage() {
        return "main_management";
    }
}
