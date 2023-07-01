package com.dev.voucherproject.controller.console;


import com.dev.voucherproject.model.menu.Menu;
import com.dev.voucherproject.view.Console;
import org.springframework.stereotype.Controller;



@Controller
public class ConsoleController {
    private final Console console;

    public ConsoleController(Console console) {
        this.console = console;
    }

    public void run() {
        while (true) {
            Menu menu;
            String input;

            console.printMenu();
            input = console.inputMenuSelection();

            menu = Menu.convertInputToMenu(input);
            menu.execute();
        }
    }
}

