package com.programmers;

import com.programmers.domain.Menu;
import com.programmers.io.Console;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

    private final Console console = new Console();

    public void run() {
        console.printMenu();

        String command = console.readInput();
        Menu menu = Menu.findMenu(command);
        System.out.println(menu.toString());
    }
}
