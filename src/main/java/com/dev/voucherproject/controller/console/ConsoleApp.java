package com.dev.voucherproject.controller.console;


import com.dev.voucherproject.model.menu.Menu;
import com.dev.voucherproject.view.Console;
import org.springframework.stereotype.Controller;


@Controller
public class ConsoleApp {
    private final Console console;
    private final MenuControllerExecutor executor;

    public ConsoleApp(Console console, MenuControllerExecutor executor) {
        this.console = console;
        this.executor = executor;
    }

    public void run() {
        while (true) {
            console.printMenu();
            String input = console.inputMenuSelection();

            Menu menu = Menu.convertInputToMenu(input);
            executor.execute(menu);
        }
    }
}

