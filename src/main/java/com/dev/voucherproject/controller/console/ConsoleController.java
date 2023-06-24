package com.dev.voucherproject.controller.console;


import com.dev.voucherproject.view.Console;
import com.dev.voucherproject.model.menu.Menu;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class ConsoleController {
    private final Console console;
    private final List<SelectMenuExecutor> executors;

    public ConsoleController(Console console, List<SelectMenuExecutor> executors) {
        this.console = console;
        this.executors = executors;
    }

    public void run() {
        Menu menu;

        while (true) {
            console.printMenu();
            menu = console.inputMenuSelection();

            if (menu == Menu.EXIT) {
                System.exit(0);
            }

            menuExecute(menu);
        }
    }

    private void menuExecute(Menu menu) {
        for (SelectMenuExecutor executor : executors) {
            executor.execute(menu);
        }
    }
}

