package com.programmers.voucher.controller;

import com.programmers.voucher.uitl.Menu;
import com.programmers.voucher.uitl.MenuType;
import com.programmers.voucher.view.Console;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication {
    private boolean isRunning = true;
    private Console console = new Console();

    public void run() {
        while (isRunning) {
            try {
                console.printMenu();
                MenuType menuType = getRequest();
                execute(menuType);
            } catch (RuntimeException e) {
                console.println(e.getMessage() + "\n");
            }
        }
    }

    private MenuType getRequest() {
        return MenuType.getCommand(console.getRequest());
    }

    public void execute(MenuType menuType) {

        switch (menuType) {
            case EXIT -> isRunning = false;
            case CREATE -> {
                console.print(Menu.CREATE_VOUCHER_TYPE_MESSAGE.getMessage()+ "\n> ");
                console.getRequest();
            }
            case LIST -> console.println("개발 중");
        }
    }
}
