package org.prgrms.kdtspringdemo.application;

import org.prgrms.kdtspringdemo.CommandType;
import org.prgrms.kdtspringdemo.console.Console;
import org.prgrms.kdtspringdemo.console.CustomerOperator;
import org.prgrms.kdtspringdemo.console.VoucherOperator;

public class ConsoleApp {
    public ConsoleApp(Console console, VoucherOperator voucherOperator, CustomerOperator customerOperator) {
        console.printStartAppInfo();

        while (true) {
            CommandType command = console.getInputCommand();
            switch (command) {
                case CREATE -> {
                    console.printCreateTypes();
                    String[] createCommand = console.getCreateLine().split(" ");
                    voucherOperator.create(createCommand);
                }
                case CUSTOMERS -> customerOperator.printAll();
                case BLACKS -> customerOperator.printBlacklist();
                case LIST -> voucherOperator.printAll();
                case EXIT -> System.exit(0);
            }
        }
    }
}
