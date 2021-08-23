package org.prgrms.kdtspringdemo.application;

import org.prgrms.kdtspringdemo.CommandType;
import org.prgrms.kdtspringdemo.console.Console;
import org.prgrms.kdtspringdemo.console.VoucherOperator;

public class CommandLineApplication {
    public static void main(String[] args) {
        var console = new Console();
        var operator = new VoucherOperator();

        console.printStartAppInfo();

        while (true) {
            CommandType command = console.getInputCommand();
            switch (command) {
                case CREATE -> {
                    console.printCreateTypes();
                    String[] createCommand = console.getCreateLine().split(" ");
                    operator.create(createCommand);
                }
                case LIST -> {
                    operator.printAll();
                }
                case EXIT -> {
                    System.exit(0);
                }
            }
        }
    }
}
