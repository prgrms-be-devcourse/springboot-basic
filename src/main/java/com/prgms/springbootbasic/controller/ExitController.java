package com.prgms.springbootbasic.controller;

import com.prgms.springbootbasic.ui.Console;
import org.springframework.stereotype.Controller;

@Controller
public class ExitController implements VoucherController {

    private final Console console;

    public ExitController(Console console) {
        this.console = console;
    }

    @Override
    public boolean run() {
        console.exit();
        return false;
    }

}
