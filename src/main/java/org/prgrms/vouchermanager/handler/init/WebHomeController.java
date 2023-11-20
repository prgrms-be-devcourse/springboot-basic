package org.prgrms.vouchermanager.handler.init;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebHomeController {

    @GetMapping("/")
    public String runApp(){
        return "home";
    }
}
