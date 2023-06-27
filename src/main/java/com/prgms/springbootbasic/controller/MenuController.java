package com.prgms.springbootbasic.controller;

import com.prgms.springbootbasic.ui.Console;
import com.prgms.springbootbasic.util.Menu;
import org.springframework.stereotype.Controller;

@Controller
public class MenuController {

    private final Console console;

    public MenuController(Console console) {
        this.console = console;
    }

    public boolean run() {
        try {
            String command = console.init();
            Menu menu = Menu.of(command);
            VoucherController commandLineController = menu.getCommandLineController(console);
            return commandLineController.run();
        } catch (Exception e) {
            console.showExceptionMessage(e.getMessage());
        }
        return true;
    }

}
