package org.prgrms.kdtspringdemo.application;

import org.prgrms.kdtspringdemo.CommandType;
import org.prgrms.kdtspringdemo.console.CommandOperator;
import org.prgrms.kdtspringdemo.console.Console;
import org.prgrms.kdtspringdemo.console.CustomerOperator;
import org.prgrms.kdtspringdemo.customer.Customer;
import org.prgrms.kdtspringdemo.voucher.Voucher;

public class ConsoleApp {
    public ConsoleApp(Console console, CommandOperator<Voucher> operator, CustomerOperator customerOperator) {
        console.printStartAppInfo();

        while (true) {
            CommandType command = console.getInputCommand();
            switch (command) {
                case CREATE -> {
                    console.printCreateSelect();
                    String num = console.getCreateLine();
                    switch (num) {
                        case "1" -> {
                            Customer customer = null;
                            while (customer == null) {
                                console.printCreateCustomerByTypes();
                                String[] createCommand = console.getCreateLine().split(" ");
                                customer = customerOperator.create(createCommand);
                            }
                        }
                        case "2" -> {
                            Voucher voucher = null;
                            while (voucher == null)
                            {
                                console.printCreateVoucherByTypes();
                                String[] createCommand = console.getCreateLine().split(" ");
                                voucher = operator.create(createCommand);
                            }
                        }
                    }
                }
                case BLACKS -> console.printList(customerOperator.getAllBlacklist());
                case LIST -> {
                    console.printListSelect();
                    String num = console.getCreateLine();
                    switch (num) {
                        case "1" -> console.printList(customerOperator.getAllitems());
                        case "2" -> console.printList(operator.getAllitems());
                    }
                }
                case EXIT -> System.exit(0);
            }
        }
    }
}
