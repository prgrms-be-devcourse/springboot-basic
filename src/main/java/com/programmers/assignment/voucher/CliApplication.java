package com.programmers.assignment.voucher;

import com.programmers.assignment.voucher.engine.controller.MenuController;
import com.programmers.assignment.voucher.util.domain.Menu;

public class CliApplication implements Runnable {
    private final MenuController menuController;

    public CliApplication(MenuController menuController) {
        this.menuController = menuController;
    }


    @Override
    public void run() {
        String command = "";
        while (true) {
            command = menuController.startMenu();

            if (command.equals(Menu.CREATE.toString())) {
                menuController.createCommand();
            }
            if (command.equals(Menu.LIST.toString())) {
                menuController.listCommand();
            }
            if (command.equals(Menu.EXIT.toString())) {
                menuController.exitCommand();
            }



        }
    }
}
