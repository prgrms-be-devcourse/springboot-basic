package com.programmers;

import com.programmers.domain.Menu;
import com.programmers.io.Console;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

    private final Console console = new Console();

    public void run() {
        boolean activated = true;

        while (activated) {
            console.printMenu();
            String command = console.readInput();
            Menu menu = Menu.findMenu(command);

            switch (menu) {
                case EXIT -> activated = false;
                default -> throw new IllegalArgumentException();
            }
        }
    }
}
