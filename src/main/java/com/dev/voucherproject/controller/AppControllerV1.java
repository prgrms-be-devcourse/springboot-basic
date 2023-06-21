package com.dev.voucherproject.controller;


import com.dev.voucherproject.view.Console;
import com.dev.voucherproject.model.Menu;
import org.springframework.stereotype.Controller;


@Controller
public class AppControllerV1 {
    private final Console console;
    private final MenuController menuController;

    public AppControllerV1(Console console, MenuController menuController) {
        this.console = console;
        this.menuController = menuController;
    }

    public void run() {
        Menu menu;

        while (true) {
            console.printMenu();
            menu = console.inputMenuSelection();

            if (menu == Menu.EXIT) {
                System.exit(0);
            }

            menuController.menuExecute(menu);
        }
    }
}

