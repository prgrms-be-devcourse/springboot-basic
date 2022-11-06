package com.prgrms.springbootbasic.handler.menu;

import com.prgrms.springbootbasic.console.Console;
import com.prgrms.springbootbasic.handler.menu.dto.MenuInputResult;
import org.springframework.stereotype.Component;

@Component
public class MenuHandler {
    private static final String COMMAND_NOT_SUPPORTER = "command not supported yet.";
    private static final String MENU = "=== Voucher Program ===\n" +
            "Type **exit** to exit the program.\n" +
            "Type **create** to create a new voucher.\n" +
            "Type **list** to list all vouchers.";
    private static final String EXIT = "Exit program. Bye.";
    private final Console console;

    public MenuHandler(Console console) {
        this.console = console;
    }

    public boolean handleBeforeExit() {
        MenuInputResult inputResult = console.printAndGetCommand(MENU);
        try {
            CommandType commandType = CommandType.findByCommand(inputResult.getCommand());
            handleMenu(commandType);
            return commandType.isExit();
        } catch (IllegalArgumentException e) {
            console.printMessage(e.getMessage());
            return false;
        }
    }

    private void handleMenu(CommandType command) {
        switch (command) {
            case EXIT -> exit();
            case CREATE -> create();
            case LIST -> list();
            default -> console.printMessage(COMMAND_NOT_SUPPORTER);
        }
    }

    private void exit() {
        console.printMessage(EXIT);
    }

    private void create() {
        System.out.println("Create Voucher. coming soon");
    }

    private void list() {
        System.out.println("Voucher List. coming soon");
    }
}
