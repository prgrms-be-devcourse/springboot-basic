package org.programmers;

import org.programmers.customer.CustomerService;
import org.programmers.voucher.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class CommandLineApplication {
    public static void main(String[] args) throws IOException {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherService = applicationContext.getBean(VoucherService.class);
        var customerService = applicationContext.getBean(CustomerService.class);

        Console console = new Console();

        var voucherFactory = new VoucherFactory();

        while (true) {
            console.printPrompt();

            String inputString = console.input("> ");
            if (inputString.equals("create")) {
                console.printVoucherTypes();

                String voucherType = console.input("> ");
                if (voucherType.equals("f")) {
                    console.askAmount();

                    long amount = Long.parseLong(console.input("> "));

                    voucherService.createVoucher("FixedAmountVoucher", amount);
                } else if (voucherType.equals("p")) {
                    console.askPercentage();

                    long percentage = Long.parseLong(console.input("> "));

                    voucherService.createVoucher("PercentDiscountVoucher", percentage);
                }
            } else if (inputString.equals("list")) {
                System.out.println(voucherService.getAllVouchers().toString());
            } else if ("blacklist".equals(inputString)) {
                System.out.println(customerService.getAllCustomersOnBlacklist().toString());
            } else if (inputString.equals("exit")) {
                break;
            }
        }
    }
}
