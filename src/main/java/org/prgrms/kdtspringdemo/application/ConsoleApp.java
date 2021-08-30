package org.prgrms.kdtspringdemo.application;

import org.prgrms.kdtspringdemo.CommandType;
import org.prgrms.kdtspringdemo.console.CommandOperator;
import org.prgrms.kdtspringdemo.console.Console;
import org.prgrms.kdtspringdemo.console.CustomerOperator;
import org.prgrms.kdtspringdemo.customer.Customer;
import org.prgrms.kdtspringdemo.voucher.Voucher;

import java.util.stream.Stream;

public class ConsoleApp {
    public ConsoleApp(Console console, CommandOperator<Voucher> operator, CustomerOperator customerOperator) {
        console.printStartAppInfo();

        while (true) {
            CommandType command = console.getInputCommand();
            switch (command) {
                case CREATE -> {
                    Voucher voucher = null;
                    while (voucher == null)
                    {
                        console.printCreateTypes();
                        String[] createCommand = console.getCreateLine().split(" ");
                        voucher = operator.create(createCommand);
                    }
                }
                case CUSTOMERS -> console.printStream(customerOperator.getAllitems());
                case BLACKS -> console.printStream(customerOperator.getAllBlacklist());
                case LIST -> console.printStream(operator.getAllitems());
                case EXIT -> System.exit(0);
            }
        }
    }
}
