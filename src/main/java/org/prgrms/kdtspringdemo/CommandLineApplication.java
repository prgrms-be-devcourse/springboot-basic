package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.Voucher.constant.CommandType;
import org.prgrms.kdtspringdemo.view.console.VoucherConsole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication {
    private final VoucherConsole voucherConsole;

    @Autowired
    public CommandLineApplication(VoucherConsole voucherConsole) {
        this.voucherConsole = voucherConsole;
    }

    public void run() {
        String userCommand = "";

        while (!userCommand.equals(CommandType.EXIT.name())) {
            voucherConsole.printInitMessage();
            userCommand = voucherConsole.InputCommand().toUpperCase();
            CommandType commandType = CommandType.findCommandType(userCommand);
            executeCommand(commandType);

        }
    }

    private void executeCommand(CommandType commandtype) {
        switch (commandtype) {
            case EXIT -> {
                voucherConsole.printSystemShutdown();
            }
            case CREATE -> {
                //create Logic
            }
            case LIST -> {
                //List Logic
            }
            default -> {
                voucherConsole.printInvalidCommandSelected();
            }
        }
    }
}
