package com.prgrms.springbootbasic.handler.menu;

import com.prgrms.springbootbasic.console.Console;
import com.prgrms.springbootbasic.handler.menu.dto.MenuInputResult;
import org.springframework.stereotype.Component;

@Component
public class MenuHandler {
    private static final String COMMAND_NOT_SUPPORTER = "command not supported yet.";

    private final Console console;

    public MenuHandler(Console console) {
        this.console = console;
    }

    public boolean handleBeforeExit() {
        MenuInputResult inputResult = console.getCommand();
        try {
            CommandType commandType = CommandType.findByCommand(inputResult.getCommand());
            handleMenu(commandType);
            return commandType.isExit();
        } catch (IllegalArgumentException e) {
            console.printInvalidMessage(e.getMessage());
            return false;
        }
    }

    private void handleMenu(CommandType command) {
        switch (command) {
            case EXIT -> exit();
            case CREATE -> create();
            case LIST -> list();
            default -> console.printInvalidMessage(COMMAND_NOT_SUPPORTER);
        }
    }

    private void exit() {
        console.printExitMessage();
    }

    private void create() {
        System.out.println("Create Voucher. coming soon");
    }

    private void list() {
        System.out.println("Voucher List. coming soon");
    }
}
