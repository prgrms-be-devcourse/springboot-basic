package com.kdt.commandLineApp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class VoucherController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showForm() {
        return "jsp/hello";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String showForm1() {
        return "views/hello";
    }
}