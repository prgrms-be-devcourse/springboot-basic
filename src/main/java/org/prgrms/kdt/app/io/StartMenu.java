package org.prgrms.kdt.app.io;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StartMenu {

    private static final String lineSeparator = System.lineSeparator();
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    public StartMenu(InputHandler inputHandler, OutputHandler outputHandler) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    public String startMenu() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Voucher Program ===");
        sb.append(lineSeparator);
        sb.append("[1] Type 'exit' to exit the program.");
        sb.append(lineSeparator);
        sb.append("[2] Type 'create' to create a new voucher.");
        sb.append(lineSeparator);
        sb.append("[3] Type 'list' to list all vouchers.");
        sb.append(lineSeparator);
        sb.append("[4] Type 'black' to list the blacklisted list.");
        sb.append(lineSeparator);

        outputHandler.outputSystemMessage(sb.toString());

        var menu = inputHandler.inputString();
        return menu;
    }

}
