package org.prgrms.kdtspringdemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WebController {
    @RequestMapping("/hello")
    public String getRequest() {
        return "Hello Spring";
    }
}
