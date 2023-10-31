package org.prgrms.prgrmsspring.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController implements ApplicationWebController {

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("/add")
    public String add() {
        return "add";
    }

    @GetMapping("/list")
    public String list() {
        return "list";
    }

    @GetMapping("/list/search/date")
    public String searchByDate() {
        return "voucher-date";
    }
}
