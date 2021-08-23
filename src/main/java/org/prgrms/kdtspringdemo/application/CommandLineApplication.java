package org.prgrms.kdtspringdemo.application;

import org.prgrms.kdtspringdemo.console.Console;
import org.prgrms.kdtspringdemo.console.VoucherOperator;

public class CommandLineApplication {
    public static void main(String[] args) {
        var console = new Console();
        var commandUtils = new VoucherOperator();

        console.printStartAppInfo();

        String command = "";
        while (true) {
            String commandLine = console.getInputCommand();
            String[] splitList = commandLine.split(" ");
            command = splitList[0];
            if (command.equals("create")) {
                console.printCreateTypes();
                String[] createCommand = console.getInputCommand().split(" ");
                commandUtils.createVoucher(createCommand);
            } else if (command.equals("list")) {
                commandUtils.printAll();
            } else if (command.equals("exit")) {
                break;
            } else {
                console.printCommandError(command);
            }
        }
    }
}
