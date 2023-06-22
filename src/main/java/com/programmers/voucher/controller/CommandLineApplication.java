package com.programmers.voucher.controller;

import com.programmers.voucher.uitl.Menu;
import com.programmers.voucher.uitl.Command;
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
                Command command = getRequest();
                execute(command);
            } catch (RuntimeException e) {
                console.println(e.getMessage() + "\n");
            }
        }
    }

    private Command getRequest() {
        return Command.getCommand(console.getRequest());
    }

    public void execute(Command command) {

        switch (command) {
            case EXIT -> isRunning = false;
            case CREATE -> {
                console.print(Menu.CREATE_VOUCHER_TYPE_MESSAGE.getMessage()+ "\n> ");
                console.getRequest();
            }
            case LIST -> console.println("개발 중");
        }
    }
}
