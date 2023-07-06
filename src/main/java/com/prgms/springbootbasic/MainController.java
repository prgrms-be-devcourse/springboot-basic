package com.prgms.springbootbasic;

import com.prgms.springbootbasic.command.CommandExecutor;
import com.prgms.springbootbasic.ui.Console;
import com.prgms.springbootbasic.util.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final Console console;
    private final CommandExecutor commandExecutor;
    public MainController(Console console, CommandExecutor commandExecutor) {
        this.console = console;
        this.commandExecutor = commandExecutor;
    }

    public void run() {
        while (isRunning());
        exit();
    }

    private boolean isRunning() {
        try {
            Menu menu = inputMenu();
            commandExecutor.executeCommand(menu);
            return isExit(menu);
        } catch (Exception e) {
            logger.error("error ===> {}", e.getMessage());
            console.showExceptionMessage(e.getMessage());
        }
        return true;
    }

    public boolean isExit(Menu menu) {
        if (menu == Menu.EXIT) {
            return false;
        }
        return true;
    }

    private void exit() {
        console.exit();
    }

    private Menu inputMenu() {
        Menu menu = Menu.of(console.init());
        return menu;
    }

}
