package com.dev.voucherproject.controller;


import com.dev.voucherproject.service.MenuService;
import com.dev.voucherproject.view.Console;
import com.dev.voucherproject.service.menus.Menu;
import org.springframework.stereotype.Controller;


@Controller
public class AppControllerV1 {
    private final Console console;
    private final MenuService menuService;

    public AppControllerV1(Console console, MenuService menuService) {
        this.console = console;
        this.menuService = menuService;
    }

    public void run() {
        Menu menu;

        while (true) {
            console.printMenu();
            menu = console.inputMenuSelection();

            if (menu == Menu.EXIT) {
                System.exit(0);
            }

            menuService.menuExecute(menu);
        }
    }
}

