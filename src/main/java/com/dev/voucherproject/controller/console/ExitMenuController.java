package com.dev.voucherproject.controller.console;

import org.springframework.stereotype.Controller;

@Controller
public class ExitMenuController implements MenuController {
    @Override
    public void execute() {
        System.exit(0);
    }
}
