package com.prgrms.springbootbasic.app;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Profile("prod | test")
@Controller
public class AppController {

    @GetMapping
    public String home(){
        return "home";
    }
}
