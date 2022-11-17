package com.programmers.kwonjoosung.springbootbasicjoosung;

import com.programmers.kwonjoosung.springbootbasicjoosung.console.Console;
import com.programmers.kwonjoosung.springbootbasicjoosung.controller.CommandType;
import com.programmers.kwonjoosung.springbootbasicjoosung.controller.MainController;
import org.springframework.stereotype.Component;

@Component
public class ApplicationLauncher {
    private final MainController controller;
    private final Console console;
    private boolean isRunning = true;

    public ApplicationLauncher(MainController controller, Console console) {
        this.controller = controller;
        this.console = console;
    }

    public void run() {
        console.startMessage();
        console.showAllCommandSet();
        while (isRunning) selectProcess();
        console.exitMessage();
    }

    private void selectProcess() {
        try {
            var command = CommandType.getCommand(console.inputCommand());
            switch (command) {
                case HELP -> console.showAllCommandSet();
                case EXIT -> exit();
                default -> controller.selectFunction(command);
            }
        } catch (RuntimeException e) {
            console.showError(e);
        }
    }

    private void exit() {
        isRunning = false;
    }
    
}
