package com.prgrms.springbootbasic.app;

import com.prgrms.springbootbasic.console.Console;
import com.prgrms.springbootbasic.handler.menu.CommandType;
import com.prgrms.springbootbasic.handler.menu.MenuHandler;
import com.prgrms.springbootbasic.handler.menu.dto.MenuInputResult;
import org.springframework.stereotype.Component;

@Component
public class VoucherApplication {

    private static final String COMMAND_NOT_SUPPORTER = "command not supported yet.";
    private static final String MENU = "=== Voucher Program ===\n" +
            "Type **exit** to exit the program.\n" +
            "Type **create** to create a new voucher.\n" +
            "Type **list** to list all vouchers.";
    private static final String EXIT_MESSAGE = "Exit program. Bye.";

    private final MenuHandler menuHandler;
    private final ApplicationStatus applicationStatus;
    private final Console console;

    public VoucherApplication(MenuHandler menuHandler, ApplicationStatus applicationStatus, Console console) {
        this.menuHandler = menuHandler;
        this.applicationStatus = applicationStatus;
        this.console = console;
    }

    public void runLifecycle() {
        while (applicationStatus.isRunning()) {
            getCommand();
        }
    }

    private void getCommand() {
        MenuInputResult inputResult = console.printAndGetCommand(MENU);
        try {
            CommandType commandType = CommandType.findByCommand(inputResult.getCommand());
            controlMenu(commandType);
        } catch (IllegalArgumentException e) {
            console.printMessage(e.getMessage());
        }
    }

    private void controlMenu(CommandType command) {
        switch (command) {
            case EXIT -> {
                applicationStatus.exit();
                console.printMessage(EXIT_MESSAGE);
            }
            case CREATE -> menuHandler.create();
            case LIST -> menuHandler.list();
            default -> console.printMessage(COMMAND_NOT_SUPPORTER);
        }
    }
}
